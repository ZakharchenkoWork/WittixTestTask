package com.faigenbloom.testtask.ui.send

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SendPageViewModel : ViewModel() {
    var onTransfer: () -> Unit = {}
    private val state: SendPageState
        get() = _stateFlow.value
    private val _stateFlow = MutableStateFlow(
        SendPageState(),
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
    }
}

data class SendPageState(
    val anount: String = "",
)
