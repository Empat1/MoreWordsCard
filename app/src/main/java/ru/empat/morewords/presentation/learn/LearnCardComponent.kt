package ru.empat.morewords.presentation.learn

import kotlinx.coroutines.flow.StateFlow

interface LearnCardComponent {

    val model: StateFlow<LearnCardStore.State>

    fun onClick()

    fun learn(wordId: Long, success: Boolean)
}