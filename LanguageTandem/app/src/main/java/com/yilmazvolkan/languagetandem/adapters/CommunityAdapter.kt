package com.yilmazvolkan.languagetandem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yilmazvolkan.languagetandem.databinding.ItemListFooterBinding
import com.yilmazvolkan.languagetandem.databinding.ItemTandemBinding
import com.yilmazvolkan.languagetandem.models.Status
import com.yilmazvolkan.languagetandem.models.TandemData

class CommunityAdapter(private val retry: () -> Unit) :
    PagedListAdapter<TandemData, RecyclerView.ViewHolder>(DataDiffCallback) {
    private var status = Status.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemTandemBinding: ItemTandemBinding =
            ItemTandemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val itemListFooterBinding: ItemListFooterBinding =
            ItemListFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == DATA_VIEW_TYPE) CommunityViewHolder(itemTandemBinding) else ListFooterViewHolder(
            retry,
            itemListFooterBinding
        )
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            getItem(position)?.let { (holder as CommunityViewHolder).bind(it) }
        else (holder as ListFooterViewHolder).bind(status)
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (status == Status.LOADING || status == Status.ERROR)
    }

    fun setState(status: Status) {
        this.status = status
        notifyItemChanged(super.getItemCount())
    }

    companion object {
        val DataDiffCallback = object : DiffUtil.ItemCallback<TandemData>() {
            override fun areItemsTheSame(
                oldItem: TandemData,
                newItem: TandemData
            ): Boolean {
                return oldItem.firstName == newItem.firstName && oldItem.pictureUrl == newItem.pictureUrl
            }

            override fun areContentsTheSame(
                oldItem: TandemData,
                newItem: TandemData
            ): Boolean {
                return oldItem == newItem
            }
        }

        private const val DATA_VIEW_TYPE = 1
        private const val FOOTER_VIEW_TYPE = 2
    }
}

