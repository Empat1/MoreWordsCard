package ru.empat.morewords.presentation.learn

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.learn.LearnCardStore.LearnCardStoreFactory

class DefaultLearnComponent @AssistedInject constructor(
    private val storeFactory: LearnCardStoreFactory,
    @Assisted("word") private val word: Word,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : LearnCardComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create(word) }

    override val model: StateFlow<LearnCardStore.State> = store.stateFlow

    override fun onClick() {
    }

    override fun onRightSwipe() {
    }

    override fun onLeftSwipe() {
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("word") word: Word,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultLearnComponent
    }
}