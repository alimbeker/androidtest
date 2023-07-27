package com.example.testing.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel: ViewModel() {
    private val _word = MutableStateFlow("")
    val word: StateFlow<String> get() = _word

    // Function to update the "Hello" text state
    fun setHelloText(text: String) {
        _word.value = text
    }
}
