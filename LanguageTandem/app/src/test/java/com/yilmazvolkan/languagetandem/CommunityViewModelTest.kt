package com.yilmazvolkan.languagetandem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.google.common.truth.Truth.assertThat
import com.yilmazvolkan.languagetandem.api.ResponseResult
import com.yilmazvolkan.languagetandem.models.Status
import com.yilmazvolkan.languagetandem.models.TandemData
import com.yilmazvolkan.languagetandem.repository.CommunityDataSource
import com.yilmazvolkan.languagetandem.repository.TandemRepository
import com.yilmazvolkan.languagetandem.viewModels.CommunityViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException


class CommunityViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    private lateinit var tandemRepository: TandemRepository

    private lateinit var communityViewModel: CommunityViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        communityViewModel = CommunityViewModel(tandemRepository)
    }

    @Test
    fun `that until page is being loaded, then status should be loading and list size should be zero`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        communityViewModel.getState().observeForever(mockedObserver)

        every { tandemRepository.getTandems(any()) } returns
                Single.never()

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.LOADING)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(0)
    }

    @Test
    fun `that page has been loaded successfully with data, then status should be success`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        val responseResult = ResponseResult(getListOfTandemData(), "", "")
        communityViewModel.getState().observeForever(mockedObserver)

        every { tandemRepository.getTandems(any()) } returns
                Single.just(responseResult)

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `that page has been loaded successfully, then data should be loaded`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        val size = 5
        val responseResult = ResponseResult(getListOfTandemData(size), "", "")
        communityViewModel.getState().observeForever(mockedObserver)

        var emitter: SingleEmitter<ResponseResult>? = null
        every { tandemRepository.getTandems(any()) } returns
                Single.create { emitter = it }

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)
        emitter?.onSuccess(responseResult)

        // Then
        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(size)
    }

    @Test
    fun `that page has been loaded with an no data, then status should be empty and list size should be zero`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        val size = 0
        val responseResult = ResponseResult(getListOfTandemData(size), "", "")
        communityViewModel.getState().observeForever(mockedObserver)

        every { tandemRepository.getTandems(any()) } returns
                Single.just(responseResult)

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.EMPTY)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(size)
    }

    @Test
    fun `that page could not been loaded, then status should be error`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        var emitter: SingleEmitter<ResponseResult>? = null
        communityViewModel.getState().observeForever(mockedObserver)

        every { tandemRepository.getTandems(any()) } returns
                Single.create { emitter = it }

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)
        emitter?.tryOnError(UnknownHostException("Unable to resolve"))

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(Status.ERROR)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(0)
    }

    @Test
    fun `that loads second page by applying pagination, then status should be success and list size should be double`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        val size = CommunityDataSource.PAGE_SIZE
        val page = 2
        val responseResult = ResponseResult(getListOfTandemData(size), "", "")
        var emitter: SingleEmitter<ResponseResult>? = null
        communityViewModel.getState().observeForever(mockedObserver)

        every { tandemRepository.getTandems(any()) } returns
                Single.create { emitter = it }

        // When
        // Load Initial page
        communityViewModel.getTandemList().observeForever(mockedDataObserver)
        emitter?.onSuccess(responseResult)

        // Load next pages
        communityViewModel.getTandemList().value?.loadAround(page)
        emitter?.onSuccess(responseResult)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(Status.SUCCESS)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }
        assertThat(slotSize.captured.size).isEqualTo(page * size)
    }

    private fun getTandemData(i: Int) =
        TandemData(
            topic = "topic$i",
            firstName = "firstName$i",
            pictureUrl = "pictureUrl$i",
            natives = listOf("en"),
            learns = listOf("fr"),
            referenceCnt = i
        )

    private fun getListOfTandemData(size: Int = CommunityDataSource.PAGE_SIZE): List<TandemData> {
        val result = mutableListOf<TandemData>()
        for (i in 1..size) {
            result.add(getTandemData(i))
        }
        return result
    }

    private fun createCommunityFeedObserver(): Observer<Status> =
        spyk(Observer { })

    private fun createCommunityDataFeedObserver(): Observer<PagedList<TandemData>> =
        spyk(Observer { })

}