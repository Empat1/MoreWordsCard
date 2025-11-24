package ru.empat.morewords.presentation.learn

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.usecase.GetOldRepeatedWordUseCase
import ru.empat.morewords.domain.usecase.RepeatWordUseCase
import ru.empat.morewords.presentation.learn.LearnCardStore.Intent
import ru.empat.morewords.presentation.learn.LearnCardStore.Label
import ru.empat.morewords.presentation.learn.LearnCardStore.State
import ru.empat.morewords.presentation.learn.LearnCardStore.State.WordState.*
import javax.inject.Inject

interface LearnCardStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object CardClick : Intent
        data class Learn(val wordId: Long, val success: Boolean) : Intent
    }

    data class State(
        val wordState: WordState
    ) {

        sealed interface WordState {
            data object Init : WordState

            data object Loading : WordState

            data object Error : WordState

            data class WordLoaded(
                val word: Word,
                val isHide: Boolean
            ) : WordState
        }
    }

    sealed interface Label {
        data object ClickBack : Label
    }
}

class LearnCardStoreFactory @Inject constructor(
    private val factory: StoreFactory,
    private val getOldRepeatedWordUseCase: GetOldRepeatedWordUseCase,
    private val repeatedWordUseCase: RepeatWordUseCase
) {

    private sealed interface Action {
        data class WordLoaded(
            val word: Word
        ) : Action
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getOldRepeatedWordUseCase().collect {
                    if (it == null) return@collect

                    dispatch(Action.WordLoaded(it))
                }
            }
        }
    }

    private sealed interface Msg {
        data class Loaded(
            val word: Word
        ) : Msg

        data class OpenCard(
            val word: Word
        ) : Msg

        data class LearnCard(val success: Boolean) : Msg
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.CardClick -> {
                    when (val state = getState.invoke().wordState) {
                        is WordLoaded -> {
                            dispatch(Msg.OpenCard(state.word))
                        }

                        else -> {}
                    }
                }

                is Intent.Learn -> {
                    scope.launch {
                        repeatedWordUseCase.invoke(intent.wordId, intent.success)
//                        dispatch(Msg.LearnCard())
                    }
                }
            }
        }

        override fun executeAction(
            action: Action,
            getState: () -> State
        ) {
            when (action) {
                is Action.WordLoaded -> {
                    dispatch(Msg.Loaded(action.word))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.Loaded -> copy(wordState = WordLoaded(msg.word, true))
            is Msg.OpenCard -> copy(wordState = WordLoaded(msg.word, false))
            is Msg.LearnCard -> TODO()
        }
    }

    fun create(): LearnCardStore =
        object : LearnCardStore, Store<Intent, State, Label> by factory.create(
            name = "LearnCardStore",
            initialState = State(Init),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}
}