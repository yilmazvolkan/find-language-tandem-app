package com.yilmazvolkan.languagetandem.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.yilmazvolkan.languagetandem.R
import com.yilmazvolkan.languagetandem.adapters.CommunityAdapter
import com.yilmazvolkan.languagetandem.databinding.FragmentCommunityBinding
import com.yilmazvolkan.languagetandem.models.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment(), CommunityAdapter.TandemItemListener {

    private val binding: FragmentCommunityBinding by inflate(R.layout.fragment_community)
    private val communityViewModel: CommunityViewModel by viewModels()
    private lateinit var communityAdapter: CommunityAdapter

    private var onBackButtonClicked: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewListeners()
        observeCommunityViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        communityAdapter = CommunityAdapter(this)
        binding.tandemRecyclerView.adapter = communityAdapter
    }

    private fun initializeViewListeners() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                onBackButtonClicked?.invoke()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, // LifecycleOwner
            callback
        )

        binding.imageViewBack.setOnClickListener {
            onBackButtonClicked?.invoke()
        }
    }

    private fun observeCommunityViewModel() = with(communityViewModel) {
        tandems.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.fetchProgress.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        communityAdapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.fetchProgress.visibility = View.VISIBLE
            }
        })
    }

    fun setOnBackButtonClicked(onBackButtonClicked: (() -> Unit)?) {
        this.onBackButtonClicked = onBackButtonClicked
    }

    override fun onClickedCharacter(tandemId: Int) {

    }

    companion object {
        fun newInstance(): CommunityFragment {
            return CommunityFragment()
        }
    }


}