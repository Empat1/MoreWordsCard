package ru.empat.morewords.presentation.add

import kotlinx.coroutines.flow.StateFlow
import ru.empat.morewords.domain.entity.Word

interface AddWordComponent {

    val model: StateFlow<AddWordStore.State>

    fun onBackClick()

    fun onClickAdd(text: String, translate: String)

    fun onChangeWord(text: String)

    fun onEditWord(word: Word)
}