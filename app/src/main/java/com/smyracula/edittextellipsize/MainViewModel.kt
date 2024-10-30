package com.smyracula.edittextellipsize

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var text = mutableStateOf("")
        private set

    var text2 = mutableStateOf("")
        private set

    fun onTextChanged2(newText: String) {
        text2.value = newText
    }

    fun onTextChanged(newText: String) {
        text.value = newText
    }
}