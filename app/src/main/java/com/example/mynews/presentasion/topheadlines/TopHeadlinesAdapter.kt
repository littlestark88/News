package com.example.mynews.presentasion.topheadlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.databinding.NewsListItemBinding
import com.example.mynews.domain.model.response.topheadlines.ArticlesTopHeadlinesList

class TopHeadlinesAdapter(
    private val onClickListener: (ArticlesTopHeadlinesList, ActivityOptionsCompat) -> Unit
): PagingDataAdapter<ArticlesTopHeadlinesList, TopHeadlinesAdapter.TopHeadlinesViewHolder>(TOPHEADLINES_DIFF_CALLBACK) {

    companion object {
        val TOPHEADLINES_DIFF_CALLBACK = object: DiffUtil.ItemCallback<ArticlesTopHeadlinesList>() {
            override fun areItemsTheSame(
                oldItem: ArticlesTopHeadlinesList,
                newItem: ArticlesTopHeadlinesList
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlesTopHeadlinesList,
                newItem: ArticlesTopHeadlinesList
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
    override fun onBindViewHolder(holder: TopHeadlinesViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlinesViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopHeadlinesViewHolder(binding)
    }

    inner class TopHeadlinesViewHolder(private val binding: NewsListItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind (data: ArticlesTopHeadlinesList) {
            with(binding) {
                tvName.text = data.title
                Glide
                    .with(itemView)
                    .load(data.urlToImage)
                    .into(imgPoster)
            }
        }

    }
}