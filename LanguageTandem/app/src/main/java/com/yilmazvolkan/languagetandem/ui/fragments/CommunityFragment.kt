package com.yilmazvolkan.languagetandem.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initializeViewListeners()
        observeCommunityViewModel()
    }

    private fun setupRecyclerView() {
        communityAdapter = CommunityAdapter()
        binding.tandemRecyclerView.apply {
            adapter = communityAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (recyclerView.canScrollVertically(1).not()) {
                        communityViewModel.retry()
                    }
                }
            })
        }
    }

    private fun initializeViewListeners() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.onBackPressed()
                }
            })

        binding.txtError.setOnClickListener { communityViewModel.retry() }
    }

    private fun observeCommunityViewModel() = with(communityViewModel) {
        getTandemList().observe(viewLifecycleOwner, {
            communityAdapter.submitList(it)
        })
        getState().observe(viewLifecycleOwner, { state ->
            binding.progressBar.visibility =
                if (state == Status.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility =
                if (state == Status.ERROR) View.VISIBLE else View.GONE
            communityAdapter.setState(state ?: Status.SUCCESS)
        })
    }

    companion object {
        fun newInstance(): CommunityFragment {
            return CommunityFragment()
        }
    }
}