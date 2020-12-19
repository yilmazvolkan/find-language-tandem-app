package com.yilmazvolkan.languagetandem.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.yilmazvolkan.languagetandem.data.database.TandemDataEntity
import com.yilmazvolkan.languagetandem.databinding.ItemTandemBinding

class CommunityViewHolder(
    private val itemBinding: ItemTandemBinding
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var tandem: TandemDataEntity

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: TandemDataEntity) {
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
    }

    override fun onClick(v: View?) {
        /** When user clicks on an item. */
    }
}