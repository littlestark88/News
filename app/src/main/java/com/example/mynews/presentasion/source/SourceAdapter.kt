package com.example.mynews.presentasion.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.databinding.ListSourceItemBinding
import com.example.mynews.databinding.NewsListItemBinding
import com.example.mynews.domain.model.response.source.SourceList

class SourceAdapter(
    private val onClickListener: (SourceList) -> Unit
): PagingDataAdapter<SourceList, SourceAdapter.SourceViewHolder>(SOURCE_DIFF_CALLBACK) {

    companion object {
        val SOURCE_DIFF_CALLBACK = object: DiffUtil.ItemCallback<SourceList>() {
            override fun areItemsTheSame(oldItem: SourceList, newItem: SourceList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SourceList, newItem: SourceList): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val binding = ListSourceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourceViewHolder(binding)
    }

    inner class SourceViewHolder(private val binding: ListSourceItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (data: SourceList) {
            with(binding) {
                tvName.text = data.name
                tvCategory.text = data.category
                container.setOnClickListener {
                    onClickListener(data)
                }
            }
        }
    }
}