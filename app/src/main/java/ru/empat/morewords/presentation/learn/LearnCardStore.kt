package ru.empat.morewords.presentation.learn

import android.content.Intent
import com.arkivanov.mvikotlin.core.store.Store
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.learn.LearnCardStore.*

interface LearnCardStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
    }

    sealed interface Label {
        data object ClickBack : Label
    }

    data class State(
        val word: Word,
        val isHide: Boolean
        //TODO forecastState
    )
}