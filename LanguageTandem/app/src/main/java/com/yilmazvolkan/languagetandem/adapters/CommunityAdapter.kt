package com.yilmazvolkan.languagetandem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yilmazvolkan.languagetandem.data.database.TandemDataEntity
import com.yilmazvolkan.languagetandem.databinding.ItemTandemBinding

class CommunityAdapter :
    RecyclerView.Adapter<CommunityViewHolder>() {

    private val items = ArrayList<TandemDataEntity>()

    fun setItems(items: ArrayList<TandemDataEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding: ItemTandemBinding =
            ItemTandemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) =
        holder.bind(items[position])
}

