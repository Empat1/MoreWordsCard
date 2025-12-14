package ru.empat.morewords.presentation.add

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.usecase.AddWordUseCase
import ru.empat.morewords.presentation.add.AddWordStore.Intent
import ru.empat.morewords.presentation.add.AddWordStore.Label
import ru.empat.morewords.presentation.add.AddWordStore.State
import ru.empat.morewords.presentation.edit.EditCardStore
import javax.inject.Inject

interface AddWordStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data class SaveWord(val text: String, val translate: String) : Intent
    }

    sealed interface State{
        data object Init : State
        data object Success : State
    }

    sealed interface Label {
        data object NavigationBack : Label
    }
}

class AddWordStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val addWordUseCase: AddWordUseCase
) {

    fun create(): AddWordStore =
        object : AddWordStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AddWordStore",
            initialState = State.Init,
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data object SaveWord : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.SaveWord -> {
                    scope.launch {
                        addWordUseCase.invoke(intent.text, intent.translate)
                        dispatch(Msg.SaveWord)
                    }
                }

                Intent.ClickBack -> publish(Label.NavigationBack)
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.SaveWord -> {
                    State.Success
                }
            }
    }
}
