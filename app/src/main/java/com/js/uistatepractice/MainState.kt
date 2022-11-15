package com.js.uistatepractice

import android.widget.Toast

class MainContract {

  // UI에 대한 모든 정의
  data class MainState(
    val isLoading: Boolean = false,
    val newsList: List<String> = emptyList()
  ): UIState {
    // MainState 필요한 함수 정의
  }

  sealed class MainEvent : UIEvent {
    // 앱의 State 를 변화시키고 필요할 경우 SideEffect 를 발생시킴, 꼭 UI 변화를 시키지는 않아도 되며 Event 에서나 Effect 에서 끝나도 된다

    data class RefreshNewsEvent(val keyword: String): MainEvent()
    data class NewsDetailClickEvent(val news: String): MainEvent()
  }

  sealed class MainEffect : UIEffect {

    // 주로 상대적으로 시간이 많이 걸리는 Background 작업들, API 통신, I/O 작업, Activity 화면의 전환 등
    data class StartNewsDetailView(val news: String): MainEffect()
    data class ShowToastMessage(val message: String, val time: Int = Toast.LENGTH_SHORT): MainEffect()
  }

}