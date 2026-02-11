package ru.empat.morewords.presentation.add

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.usecase.AddWordUseCase
import ru.empat.morewords.domain.usecase.GetWordUseCase
import ru.empat.morewords.domain.usecase.ValidationWordUseCase
import ru.empat.morewords.presentation.add.AddWordStore.Intent
import ru.empat.morewords.presentation.add.AddWordStore.Label
import ru.empat.morewords.presentation.add.AddWordStore.State
import ru.empat.morewords.presentation.add.AddWordStore.State.Input
import ru.empat.morewords.presentation.add.AddWordStore.State.Input.Error.*
import javax.inject.Inject

interface AddWordStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data class SaveWord(val text: String, val translate: String) : Intent

        data class InputWord(val text: String, val translate: String) : Intent
    }

    sealed interface State {
        data object Init : State
        data object Success : State

        sealed interface Input : State {
            data object Conform : Input

            sealed interface Error : Input {
                data class HasWord(val word: Word) : Error
                data class Validation(val text: Int) : Error
            }
        }

        data class Error(val word: Word) : State
    }

    sealed interface Label {
        data object NavigationBack : Label
    }
}

class AddWordStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val addWordUseCase: AddWordUseCase,
    private val getWordUseCase: GetWordUseCase,
    private val validationWordUseCase: ValidationWordUseCase
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

        data class Error(val word: Word) : Msg

        data object ConformInput : Msg
        data class HasWord(val word: Word) : Msg
        data class ValidationError(val text: Int) : Msg
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
                is Intent.InputWord -> {
                    scope.launch {
                        val validationError =
                            validationWordUseCase.getErrorOrNull(intent.text, intent.translate)

                        if(validationError != null){
                            dispatch(Msg.ValidationError(validationError))
                        }
                        
                        getWordUseCase(intent.text).collect {
                            if (it != null) {
                                dispatch(Msg.HasWord(it))
                            } else {
                                dispatch(Msg.ConformInput)
                            }
                        }
                    }
                }
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

                is Msg.Error -> {
                    State.Error(message.word)
                }

                Msg.ConformInput -> {
                    Input.Conform
                }

                is Msg.HasWord -> {
                    HasWord(message.word)
                }

                is Msg.ValidationError -> {
                    Validation(message.text)
                }
            }
    }
}
