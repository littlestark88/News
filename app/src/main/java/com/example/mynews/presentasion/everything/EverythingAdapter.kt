package com.example.mynews.presentasion.everything

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.R
import com.example.mynews.databinding.NewsListItemBinding
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList

class EverythingAdapter(
    private val onClickListener: (ArticlesEverythingList) -> Unit
): PagingDataAdapter<ArticlesEverythingList, EverythingAdapter.EverythingViewHolder>(EVERYTHING_DIFF_CALLBACK) {

    companion object {
        val EVERYTHING_DIFF_CALLBACK = object: DiffUtil.ItemCallback<ArticlesEverythingList>() {
            override fun areItemsTheSame(
                oldItem: ArticlesEverythingList,
                newItem: ArticlesEverythingList
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlesEverythingList,
                newItem: ArticlesEverythingList
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: EverythingViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EverythingViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EverythingViewHolder(binding)
    }

    inner class EverythingViewHolder(private val binding: NewsListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind (data: ArticlesEverythingList) {
                with(binding) {
                    tvName.text = data.title
                    Glide
                        .with(itemView)
                        .load(data.urlToImage)
                        .placeholder(R.drawable.ic_refresh)
                        .into(imgPoster)
                    container.setOnClickListener {
                        onClickListener(data)
                    }
                }
            }
    }
}