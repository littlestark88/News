package com.example.mynews.presentasion.topheadlines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mynews.databinding.ActivityTopheadlinesDetailBinding
import com.example.mynews.domain.model.response.topheadlines.ArticlesTopHeadlinesList
import com.example.mynews.utils.Const.DATA_TOPHEADLINES

class TopheadlinesDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopheadlinesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopheadlinesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<ArticlesTopHeadlinesList>(DATA_TOPHEADLINES)
        with(binding) {
            tvTitle.text = data?.title
            tvDescription.text = data?.description
            tvPublishedDate.text = data?.publishedApi
            Glide
                .with(this@TopheadlinesDetailActivity)
                .load(data?.urlToImage)
                .into(imgPhoto)
        }
    }
}