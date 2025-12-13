package ru.empat.morewords.presentation.learn

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import ru.empat.morewords.domain.entity.Word

class DefaultLearnComponent @AssistedInject constructor(
    private val storeFactory: LearnCardStoreFactory,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : LearnCardComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<LearnCardStore.State> = store.stateFlow

    override fun onClick() {
        store.accept(LearnCardStore.Intent.CardClick)
    }

    override fun learn(wordId: Long, success: Boolean) {
        store.accept(LearnCardStore.Intent.Learn(wordId, success))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onBackClicked") onBackClicked: () -> Unit
        ): DefaultLearnComponent
    }
}