package com.abhi.deliverylist.adapters

import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ObservableBoolean
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhi.deliverylist.AppController
import com.abhi.deliverylist.R
import com.abhi.deliverylist.databinding.DeliveriesListItemBinding
import com.abhi.deliverylist.databinding.DeliveriesListLoadingItemBinding
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.abhi.deliverylist.ui.DetailActivity
import com.squareup.picasso.Picasso


const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_LOADING = 1

class DeliveryListAdapter () : PagedListAdapter<DeliveryItem,
        DeliveryListAdapter.BaseViewHolder<*>>(mDiffCallback) {

    private var loading = ObservableBoolean(false)
    private var loadMore = ObservableBoolean(false)

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount())
            VIEW_TYPE_ITEM
        else VIEW_TYPE_LOADING


    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasLoadingFooter()) 1 else 0
    }

    private fun hasLoadingFooter(): Boolean {
        return super.getItemCount() != 0 && (loading.get() || loadMore.get())
    }

    fun setLoading(loading: Boolean, loadMore: Boolean) {
        this.loadMore.set(loadMore)
        this.loading.set(loading)
        notifyItemChanged(super.getItemCount())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        if(viewType== VIEW_TYPE_ITEM)
        return ItemHolder(
            DeliveriesListItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false
            )
        )
        else
            return ItemViewHolder(DeliveriesListLoadingItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent, false
            ))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(0 as Int)
            }
            is ItemHolder -> {
                val element = getItem(position) as DeliveryItem
                holder.bind(element as DeliveryItem)
            }
            else -> throw IllegalArgumentException()
        }
    }


    class ItemHolder(
        private val binding: DeliveriesListItemBinding
    ) :
        BaseViewHolder<DeliveryItem>(binding.root) {

        lateinit var deliveryItem: DeliveryItem
        override fun bind(deliveryItem: DeliveryItem) {
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

    inner class ItemViewHolder( private val binding: DeliveriesListLoadingItemBinding)
        : BaseViewHolder<Int>(binding.root) {

        override fun bind(item: Int) {
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

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
}
