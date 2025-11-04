package ru.empat.morewords.presentation.education

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.empat.morewords.presentation.education.EducationStore.Intent
import ru.empat.morewords.presentation.education.EducationStore.Label
import ru.empat.morewords.presentation.education.EducationStore.State
import javax.inject.Inject

interface EducationStore : Store<Intent, State, Label>{

    sealed interface Intent {
        data object ClickLearn: Intent
        data object ClickGetList: Intent
        data object ClickAddCard: Intent
    }

    sealed interface State{
        data object Init : State
        data object Loading : State
        data object Error : State

        data class Loaded(
            val wordForLearn: Int,
            val wordForRepeat: Int,
            val completeWord: Int
        ) : State
    }

    sealed interface Label {
        data object ClickLearn: Label
        data object ClickGetList: Label
        data object ClickAddCard: Label
    }
}

class EducationStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory
    //TODO usecase
) {

    fun create() : EducationStore =
        object : EducationStore, Store<Intent, State, Label> by storeFactory.create(
            name = "StoreFactory",
            initialState = State.Loaded(1,2,3),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class Loaded(
            val wordForLearn: Int,
            val wordForRepeat: Int,
            val completeWord: Int
        ) : Action
    }

    private sealed interface Msg {
        data class Loaded(
            val wordForLearn: Int,
            val wordForRepeat: Int,
            val completeWord: Int
        ) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            //todo loading from room
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent){
                Intent.ClickAddCard -> {
                    publish(Label.ClickAddCard)
                }
                Intent.ClickGetList -> {
                    publish(Label.ClickGetList)
                }
                Intent.ClickLearn -> {
                    publish(Label.ClickLearn)
                }
            }
        }

        override fun executeAction(
            action: Action,
            getState: () -> State
        ) {
            super.executeAction(action, getState)
            //TODO this
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.Loaded -> {
                State.Loaded(msg.wordForLearn, msg.wordForRepeat, msg.completeWord)
            }
        }
    }
}