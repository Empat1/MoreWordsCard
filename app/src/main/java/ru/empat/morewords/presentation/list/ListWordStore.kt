package ru.empat.morewords.presentation.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.usecase.GetAllWordsUseCase
import ru.empat.morewords.domain.usecase.RemoveWordUseCase
import ru.empat.morewords.presentation.list.ListWordStore.Intent
import ru.empat.morewords.presentation.list.ListWordStore.Label
import ru.empat.morewords.presentation.list.ListWordStore.State
import ru.empat.morewords.presentation.list.ListWordStoreFactory.Msg.*
import javax.inject.Inject

interface ListWordStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ClickWord(val word: Word) : Intent
        data class RemoveWord(val word: Word) : Intent
    }

    sealed interface State {
        data object Init : State
        data object Loading : State
        data class Loaded(val words: List<Word>) : State
    }

    sealed interface Label {
        data class OpenEditWord(val word: Word) : Label
        data object ClickBack : Label
    }
}

class ListWordStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val removeWordUseCase: RemoveWordUseCase
) {

    fun create(): ListWordStore =
        object : ListWordStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ListWordStore",
            initialState = State.Init,
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class Loaded(val word: List<Word>) : Action
        data object Loading : Action
    }

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val words: List<Word>) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.Loading)
                getAllWordsUseCase.invoke().collect {
                    dispatch(Action.Loaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ClickWord -> {
                    publish(Label.OpenEditWord(intent.word))
                }

                is Intent.RemoveWord -> {
                    scope.launch {
                        removeWordUseCase.invoke(intent.word.wordId)
                        publish(Label.ClickBack)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.Loaded -> {
                    dispatch(Loaded(action.word))
                }

                Action.Loading -> dispatch(Loading)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Loaded -> {
                    State.Loaded(msg.words)
                }

                Loading -> {
                    State.Loading
                }
            }
    }
}
