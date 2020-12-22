package com.yilmazvolkan.languagetandem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.google.common.truth.Truth.assertThat
import com.yilmazvolkan.languagetandem.data.api.ResponseResult
import com.yilmazvolkan.languagetandem.models.Status
import com.yilmazvolkan.languagetandem.models.TandemData
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
    fun `that first page has been loaded successfully with a data,  then status should be success`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        communityViewModel.getState().observeForever(mockedObserver)

        val responseResult = ResponseResult(getListOfTandemData(size = 1), "", "")

        every { tandemRepository.getTandems(any()) } returns
                Single.just(responseResult)

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.SUCCESS)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(1)
    }


    @Test
    fun `that first page has not been loaded yet, then status should be loading`() {
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
    }

    @Test
    fun `that first page has about to been loaded, then status should switch from loading to success`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        communityViewModel.getState().observeForever(mockedObserver)

        val responseResult = ResponseResult(getListOfTandemData(size = 1), "", "")

        var emitter: SingleEmitter<ResponseResult>? = null
        every { tandemRepository.getTandems(any()) } returns
                Single.create { emitter = it }

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.LOADING)

        emitter?.onSuccess(responseResult)

        verify { mockedObserver.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(Status.SUCCESS)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(1)
    }

    @Test
    fun `that first page has been loaded with an empty list, then status should be error`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        communityViewModel.getState().observeForever(mockedObserver)

        val responseResult = ResponseResult(getListOfTandemData(size = 0), "", "")

        every { tandemRepository.getTandems(any()) } returns
                Single.just(responseResult)

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.ERROR)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(0)
    }

    @Test
    fun `that first page could not been loaded, then status should be error`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        communityViewModel.getState().observeForever(mockedObserver)


        var emitter: SingleEmitter<ResponseResult>? = null
        every { tandemRepository.getTandems(any()) } returns
                Single.create { emitter = it }

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.LOADING)

        emitter?.tryOnError(UnknownHostException("Unable to resolve"))

        verify { mockedObserver.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(Status.ERROR)

        val slotSize = slot<PagedList<TandemData>>()
        verify { mockedDataObserver.onChanged(capture(slotSize)) }

        assertThat(slotSize.captured.size).isEqualTo(0)
    }

    @Test
    fun `that first two pages has been loaded and third page is not, then status should be changed to success to error`() {
        // Given
        val mockedDataObserver = createCommunityDataFeedObserver()
        val mockedObserver = createCommunityFeedObserver()
        communityViewModel.getState().observeForever(mockedObserver)

        val responseResult = ResponseResult(getListOfTandemData(size = 1), "", "")

        var emitter: SingleEmitter<ResponseResult>? = null
        every { tandemRepository.getTandems(any()) } returns
                Single.create { emitter = it }

        // When
        communityViewModel.getTandemList().observeForever(mockedDataObserver)

        // Then
        val slot = slot<Status>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(Status.LOADING)

        emitter?.onSuccess(responseResult)

        verify { mockedObserver.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(Status.SUCCESS)

        emitter?.onSuccess(responseResult)

        verify { mockedObserver.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(Status.SUCCESS)

        emitter?.tryOnError(UnknownHostException("Unable to resolve"))

        verify { mockedObserver.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(Status.ERROR)
    }

    private fun getTandemData() =
        TandemData(
            topic = "topic",
            firstName = "firstName",
            pictureUrl = "pictureUrl",
            natives = listOf("en"),
            learns = listOf("fr"),
            referenceCnt = 999
        )

    private fun getListOfTandemData(size: Int): List<TandemData> {
        val result = mutableListOf<TandemData>()
        for (i in 1..size) {
            result.add(getTandemData())
        }
        return result
    }

    private fun createCommunityFeedObserver(): Observer<Status> =
        spyk(Observer { })

    private fun createCommunityDataFeedObserver(): Observer<PagedList<TandemData>> =
        spyk(Observer { })

}