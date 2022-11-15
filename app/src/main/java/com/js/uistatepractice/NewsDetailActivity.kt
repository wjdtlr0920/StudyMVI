package com.js.uistatepractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.js.uistatepractice.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

  lateinit var binding: ActivityNewsDetailBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)
    binding.apply {
      lifecycleOwner = this@NewsDetailActivity
    }

    binding.tvTitle.text = intent.getStringExtra("NEWS_RESPONSE") ?: "일시적인 오류 입니다"

  }
}