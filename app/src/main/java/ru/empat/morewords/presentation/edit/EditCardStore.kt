package ru.empat.morewords.presentation.edit

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.usecase.EditWordUseCase
import ru.empat.morewords.domain.usecase.RemoveWordUseCase
import ru.empat.morewords.presentation.edit.EditCardStore.Intent
import ru.empat.morewords.presentation.edit.EditCardStore.Label
import ru.empat.morewords.presentation.edit.EditCardStore.State
import ru.empat.morewords.presentation.edit.EditCardStoreFactory.Msg.*
import javax.inject.Inject

interface EditCardStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data class EditWord(val text: String, val translate: String) : Intent
        data class RemoveWord(val wordId: Long) : Intent
    }

    data class State(
        val word: Word
    )

    sealed interface Label {
        data object NavigationBack : Label
        data object SaveWord: Label
    }
}

class EditCardStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val editWordUseCase: EditWordUseCase,
    private val removeWordUseCase: RemoveWordUseCase
) {

    fun create(word: Word): EditCardStore =
        object : EditCardStore, Store<Intent, State, Label> by storeFactory.create(
            name = "EditCardStore",
            initialState = State(word),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
        data class EditedWord(val word: Word) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.NavigationBack)
                }

                is Intent.EditWord -> {
                    scope.launch {
                        val newWord = getState.invoke()
                            .word.copy(
                                text = intent.text,
                                translate = intent.translate
                            )

                        editWordUseCase.invoke(newWord)
                        publish(Label.SaveWord)
                        dispatch(EditedWord(newWord))
                    }
                }

                is Intent.RemoveWord -> {
                    scope.launch {
                        removeWordUseCase.invoke(intent.wordId)
                        publish(Label.NavigationBack)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.EditedWord -> {
                    copy(msg.word)
                }
            }
    }
}
