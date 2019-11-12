package com.abhi.deliverylist.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhi.deliverylist.AppController
import com.abhi.deliverylist.R
import com.abhi.deliverylist.databinding.DeliveriesListItemBinding
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.abhi.deliverylist.ui.DetailActivity
import com.squareup.picasso.Picasso


const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_LOADING = 1

class DeliveryListAdapter () : ListAdapter<DeliveryItem, RecyclerView.ViewHolder>(mDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount())
            VIEW_TYPE_ITEM
        else VIEW_TYPE_LOADING
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ItemHolder(
            DeliveriesListItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemHolder).bind(getItem(position) as DeliveryItem)

    }


    class ItemHolder(
        private val binding: DeliveriesListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var deliveryItem: DeliveryItem
        fun bind(deliveryItem: DeliveryItem) {
            binding.tvName.text = deliveryItem.description
            Picasso.get().load(deliveryItem.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ivDelivery)

            binding.rootLayout.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("delivery_item", deliveryItem)
                it.context.startActivity(intent)
            }

        }
    }


    companion object {
        val mDiffCallback = object : DiffUtil.ItemCallback<DeliveryItem>() {
            override fun areItemsTheSame(oldItem: DeliveryItem, newItem: DeliveryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DeliveryItem, newItem: DeliveryItem): Boolean {
                return oldItem == newItem
            }
        }
    }


    @VisibleForTesting
    fun getDeliveryItem(position: Int): DeliveryItem? {
        return super.getItem(position)
    }
}