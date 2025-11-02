package ru.empat.morewords.presentation.learn

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.learn.LearnCardStore.*
import javax.inject.Inject

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

    class LearnCardStoreFactory @Inject constructor(
        private val factory: StoreFactory,
        //todo usecase
    ) {

        private sealed interface Action

        private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
            override fun invoke() {
            }
        }

        private sealed interface Msg {
            data object L : Msg
        }

        private inner class ExecutorImpl() : CoroutineExecutor<Intent, Action, State, Msg, Label>(){
            override fun executeIntent(intent: LearnCardStore.Intent, getState: () -> State) {
                super.executeIntent(intent, getState)
            }
        }

        private object ReducerImpl : Reducer<State, Msg> {
            override fun State.reduce(msg: Msg): State {
                return State(Word(1, 1, "" , ""), false)
            }
        }

        fun create(word: Word): LearnCardStore =
            object : LearnCardStore, Store<Intent, State, Label> by factory.create(
                name = "LearnCardStore",
                        initialState = State(word, true),
                        bootstrapper = BootstrapperImpl(),
                        executorFactory = ::ExecutorImpl,
                        reducer  = ReducerImpl
            ) {}
    }
}