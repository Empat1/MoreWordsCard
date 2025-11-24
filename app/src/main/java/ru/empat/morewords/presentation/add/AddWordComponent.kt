package ru.empat.morewords.presentation.add

import kotlinx.coroutines.flow.StateFlow

interface AddWordComponent {

    val model: StateFlow<AddWordStore.State>

    fun onClickAdd(text: String, translate: String)
}