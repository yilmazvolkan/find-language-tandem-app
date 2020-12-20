package com.yilmazvolkan.languagetandem.adapters

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.yilmazvolkan.languagetandem.databinding.ItemListFooterBinding
import com.yilmazvolkan.languagetandem.models.Status


class ListFooterViewHolder(
    private val retry: () -> Unit,
    private val itemBinding: ItemListFooterBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(status: Status?) {
        itemBinding.progressBar.visibility = if (status == Status.LOADING) VISIBLE else INVISIBLE
        itemBinding.txtError.visibility = if (status == Status.ERROR) VISIBLE else INVISIBLE

        itemBinding.txtError.setOnClickListener { retry() }
    }
}