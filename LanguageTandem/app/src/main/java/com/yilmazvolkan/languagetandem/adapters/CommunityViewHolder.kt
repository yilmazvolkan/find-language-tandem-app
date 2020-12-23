package com.yilmazvolkan.languagetandem.adapters

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.yilmazvolkan.languagetandem.databinding.ItemTandemBinding
import com.yilmazvolkan.languagetandem.models.TandemData

class CommunityViewHolder(
    private val itemBinding: ItemTandemBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var tandem: TandemData

    fun bind(item: TandemData) {
        this.tandem = item
        changeViewAttributes()
    }

    private fun changeViewAttributes() {
        itemBinding.textViewName.text = tandem.firstName
        itemBinding.textViewReference.text = tandem.referenceCnt.toString()
        itemBinding.textViewDesc.text = tandem.topic

        itemBinding.textViewLearnUser.text = tandem.getLearnsString()
        itemBinding.textViewNativeUser.text = tandem.getNativeString()

        Glide.with(itemBinding.root)
            .load(tandem.pictureUrl)
            .transform(CircleCrop())
            .into(itemBinding.image)

        if (tandem.isNew()) {
            itemBinding.textViewNew.visibility = View.VISIBLE
            itemBinding.textViewReference.visibility = View.GONE
        }
        else{
            itemBinding.textViewNew.visibility = View.GONE
            itemBinding.textViewReference.visibility = View.VISIBLE
        }
    }
}