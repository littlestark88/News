package com.example.mynews.presentasion.source

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.mynews.databinding.ActivitySourceDetailBinding
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList
import com.example.mynews.domain.model.response.source.SourceList
import com.example.mynews.utils.Const

class SourceDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySourceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySourceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<SourceList>(Const.DATA_SOURCE)
        with(binding) {
            wbDetail.webViewClient = WebViewClient()
            wbDetail.loadUrl(data?.url.toString())
            wbDetail.settings.javaScriptEnabled = true
            wbDetail.settings.setSupportZoom(true)
        }
    }

    override fun onBackPressed() {
        if (binding.wbDetail.canGoBack())
            binding.wbDetail.goBack()
        else
            super.onBackPressed()
    }
}