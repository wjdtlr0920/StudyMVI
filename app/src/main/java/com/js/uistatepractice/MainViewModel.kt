package com.js.uistatepractice

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel<MainContract.MainState, MainContract.MainEvent, MainContract.MainEffect>() {

  override fun initializeState(): MainContract.MainState = MainContract.MainState()

  override fun handleEvent(event: MainContract.MainEvent) {
    when(event) {
      is MainContract.MainEvent.RefreshNewsEvent -> {

        if (event.keyword.contains("가나다")) {
          setEffect { MainContract.MainEffect.ShowToastMessage("\"가나다\"가 포함된 검색어로는 검색할 수 없습니다.") }
          return
        }

        viewModelScope.launch {
          updateState { currentState.copy(isLoading = true) }
          // News 받아오기
          delay(1000)
          val newsResponse = run {
            List(1000) { index -> "Topic $index"}
          }
          // News 받아온 후
          updateState { currentState.copy(isLoading = false, newsList = newsResponse) }
        }
      }

      is MainContract.MainEvent.NewsDetailClickEvent -> {
        setEffect { MainContract.MainEffect.StartNewsDetailView(event.news)}
      }
    }
  }

  fun requestNewsList() {
    viewModelScope.launch {
    }
  }
}