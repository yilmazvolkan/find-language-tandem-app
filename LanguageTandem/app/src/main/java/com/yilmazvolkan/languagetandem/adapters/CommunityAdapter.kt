package com.yilmazvolkan.languagetandem.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.yilmazvolkan.languagetandem.data.database.Converters
import com.yilmazvolkan.languagetandem.data.database.TandemDataEntity
import com.yilmazvolkan.languagetandem.databinding.ItemTandemBinding
import java.util.*
import kotlin.collections.ArrayList

class CommunityAdapter(private val listener: TandemItemListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    interface TandemItemListener {
        fun onClickedCharacter(tandemId: Int)
    }

    private val items = ArrayList<TandemDataEntity>()

    fun setItems(items: ArrayList<TandemDataEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemTandemBinding =
            ItemTandemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(items[position])
}

class CharacterViewHolder(
    private val itemBinding: ItemTandemBinding,
    private val listener: CommunityAdapter.TandemItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var tandem: TandemDataEntity

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: TandemDataEntity) {
        this.tandem = item
        itemBinding.textViewName.text = item.firstName
        itemBinding.textViewReference.text = item.referenceCnt.toString()
        itemBinding.textViewDesc.text = item.topic

        itemBinding.textViewLearnUser.text = Converters.toString(item.learns).toUpperCase(Locale.ROOT)
        itemBinding.textViewNativeUser.text = Converters.toString(item.natives).toUpperCase(Locale.ROOT)

        Glide.with(itemBinding.root)
            .load(item.pictureUrl)
            .transform(CircleCrop())
            .into(itemBinding.image)

    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(tandem.id)
    }
}

