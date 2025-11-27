package ru.empat.morewords.presentation.edit

import kotlinx.coroutines.flow.StateFlow

interface EditCardComponent {
    val model : StateFlow<EditCardStore.State>

    fun onBackClick()

    fun editClick(text: String, translate: String)

    fun removeClick(wordId: Long)
}