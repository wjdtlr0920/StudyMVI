package com.js.uistatepractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<ST : UIState, EV : UIEvent, EF : UIEffect> : ViewModel() {
  private val initialState by lazy { initializeState() }

  private val _state = MutableStateFlow(initialState)
  val state = _state.asStateFlow()

  val currentState: ST
    get() = state.value

  private val _effect = Channel<EF>()
  open val effect = _effect
    .receiveAsFlow()

  private val _event = MutableSharedFlow<EV>()
  val event = _event.asSharedFlow()

  fun setEvent(e: EV) {
    viewModelScope.launch {
      _event.emit(e)
    }
  }

  init {
    subscribeEvent()
  }

  override fun onCleared() {
    super.onCleared()
    _effect.close()
  }

  protected abstract fun handleEvent(event: EV)

  abstract fun initializeState(): ST

  protected fun updateState(builder: () -> ST) {
    _state.value = builder()
  }

  protected fun setEffect(builder: () -> EF) {
    val effectValue = builder()
    viewModelScope.launch { _effect.send(effectValue) }
  }

  private fun subscribeEvent() = viewModelScope.launch {
    event.collect { handleEvent(it) }
  }
}