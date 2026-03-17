package ru.empat.morewords.presentation.education

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.usecase.GetCountAllWordsUseCase
import ru.empat.morewords.domain.usecase.GetCountRepeatWordsUseCase
import ru.empat.morewords.domain.usecase.GetLanguagesUseCase
import ru.empat.morewords.presentation.education.EducationStore.Intent
import ru.empat.morewords.presentation.education.EducationStore.Label
import ru.empat.morewords.presentation.education.EducationStore.State
import javax.inject.Inject

interface EducationStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickLearn : Intent
        data object ClickGetList : Intent
        data object ClickAddCard : Intent
    }

    data class State(
        val language: List<Language>,
        val statisticState: StatisticState
    ) {

        sealed interface StatisticState {
            data object Init : StatisticState
            data object Loading : StatisticState
            data object Error : StatisticState

            data class Loaded(
                val wordForLearn: Int,
                val deyStreak: Int,
                val completeWord: Int
            ) : StatisticState
        }
    }

    sealed interface Label {
        data object ClickLearn : Label
        data object ClickGetList : Label
        data object ClickAddCard : Label
    }
}

class EducationStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getLanguageUseCase: GetLanguagesUseCase,
    private val getCountAllWordsUseCase: GetCountAllWordsUseCase,
    private val getCountRepeatWordsUseCase: GetCountRepeatWordsUseCase
) {

    fun create(): EducationStore =
        object : EducationStore, Store<Intent, State, Label> by storeFactory.create(
            name = "StoreFactory",
            initialState = State(
                emptyList(),
                State.StatisticState.Init
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class Loaded(
            val countAllWords: Int,
            val countLearnWords: Int,
            val dayStreak: Int
        ) : Action

        data class LanguagesLoaded(
            val language: List<Language>
        ) : Action
    }

    private sealed interface Msg {
        data class Loaded(
            val countAllWords: Int,
            val countLearnWords: Int,
            val dayStreak: Int
        ) : Msg

        data class LanguageLoaded(
            val list: List<Language>
        ) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {

                launch {
                    getLanguageUseCase().collect {
                        dispatch(Action.LanguagesLoaded(it))
                    }
                }

                launch {
                    combine(
                        getCountAllWordsUseCase.invoke(),
                        getCountRepeatWordsUseCase.invoke(),
                        flow {
                            emit(1)
                        }
                    ) { countAll, countRepeat, day ->
                        Action.Loaded(countAll, countRepeat, day)
                    }.collect {
                        dispatch(it)
                    }
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
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
            when (action) {
                is Action.LanguagesLoaded -> {
                    dispatch(Msg.LanguageLoaded(action.language))
                }

                is Action.Loaded -> {
                    dispatch(
                        Msg.Loaded(
                            countAllWords = action.countAllWords,
                            countLearnWords = action.countLearnWords,
                            dayStreak = action.dayStreak
                        )
                    )
                }

                else -> {}
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.Loaded -> {
                copy(
                    statisticState = State.StatisticState.Loaded(
                        msg.countAllWords,
                        msg.countLearnWords,
                        msg.dayStreak,
                    )
                )
            }

            is Msg.LanguageLoaded -> {
                copy(language = msg.list)
            }
        }
    }
}