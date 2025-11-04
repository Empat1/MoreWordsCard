package ru.empat.morewords.presentation.education

import kotlinx.coroutines.flow.StateFlow
import ru.empat.morewords.domain.entity.Word

interface EducationComponent {

    val model: StateFlow<EducationStore.State>

    fun onClickEducation()

    fun onShowList()

    fun addWord(card: Word)
}