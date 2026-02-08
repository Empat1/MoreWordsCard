package ru.empat.morewords.presentation.list

import kotlinx.coroutines.flow.StateFlow
import ru.empat.morewords.domain.entity.Word

interface ListWordComponent {

    val model: StateFlow<ListWordStore.State>

    fun clickBack()

    fun openWord(word: Word)

    fun remove(word: Word)
}