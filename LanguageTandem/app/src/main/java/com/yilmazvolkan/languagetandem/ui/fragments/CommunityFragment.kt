package com.yilmazvolkan.languagetandem.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yilmazvolkan.languagetandem.R
import com.yilmazvolkan.languagetandem.adapters.CommunityAdapter
import com.yilmazvolkan.languagetandem.databinding.FragmentCommunityBinding
import com.yilmazvolkan.languagetandem.models.Status
import com.yilmazvolkan.languagetandem.viewModels.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment() {

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
        communityAdapter = CommunityAdapter { communityViewModel.retry() }
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
        binding.txtError.setOnClickListener { communityViewModel.retry() }
    }

    private fun observeCommunityViewModel() = with(communityViewModel) {
        getTandemList().observe(viewLifecycleOwner, {
            communityAdapter.submitList(it)
        })
        getState().observe(viewLifecycleOwner, { state ->
            binding.progressBar.visibility = if (communityViewModel.listIsEmpty() && state == Status.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (communityViewModel.listIsEmpty() && state == Status.ERROR) View.VISIBLE else View.GONE
            if (communityViewModel.listIsEmpty().not()) {
                communityAdapter.setState(state ?: Status.SUCCESS)
            }
        })
    }

    fun setOnBackButtonClicked(onBackButtonClicked: (() -> Unit)?) {
        this.onBackButtonClicked = onBackButtonClicked
    }

    companion object {
        fun newInstance(): CommunityFragment {
            return CommunityFragment()
        }
    }
}