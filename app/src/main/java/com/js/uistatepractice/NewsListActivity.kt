package com.js.uistatepractice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.js.uistatepractice.databinding.ActivityNewsListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel by viewModels()

  lateinit var binding: ActivityNewsListBinding

  val newsAdapter: NewsAdapter by lazy { NewsAdapter { mainViewModel.setEvent(MainContract.MainEvent.NewsDetailClickEvent(it)) } }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)
    binding.apply {
      lifecycleOwner = this@NewsListActivity
      view = this@NewsListActivity
      vm = mainViewModel
    }

    collectState()
    collectEffect()

    binding.ivSearch.setOnClickListener {
      mainViewModel.setEvent(MainContract.MainEvent.RefreshNewsEvent(binding.etKeyword.text.toString()))
    }
  }

  private fun collectState() {
    CoroutineScope(Dispatchers.Main).launch {
      mainViewModel.state
        .collect {
          newsAdapter.submitList(it.newsList)
        }
    }
  }

  @SuppressLint("WrongConstant")
  private fun collectEffect() {
    CoroutineScope(Dispatchers.Main).launch {
      mainViewModel.effect
        .collect {
          when (it) {
            // 화면 전환
            is MainContract.MainEffect.StartNewsDetailView -> {
              // 상세 페이지 전환
              startActivity(Intent(this@NewsListActivity, NewsDetailActivity::class.java).apply {
                putExtra("NEWS_RESPONSE", it.news)
              })
            }

            // 토스트 메세지
            is MainContract.MainEffect.ShowToastMessage -> {
              Toast.makeText(this@NewsListActivity, it.message, Toast.LENGTH_LONG).show()
            }
          }
        }
    }
  }
}