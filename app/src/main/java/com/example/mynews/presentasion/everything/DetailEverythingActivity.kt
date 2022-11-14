package com.example.mynews.presentasion.everything

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mynews.databinding.ActivityDetailEverythingBinding
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList
import com.example.mynews.utils.Const.DATA_EVERYTHING

class DetailEverythingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEverythingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEverythingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<ArticlesEverythingList>(DATA_EVERYTHING)
        with(binding) {
            tvTitle.text = data?.title
            tvDescription.text = data?.description
            tvPublishedDate.text = data?.publishedApi
            Glide
                .with(this@DetailEverythingActivity)
                .load(data?.urlToImage)
                .into(imgPhoto)
        }
    }
}