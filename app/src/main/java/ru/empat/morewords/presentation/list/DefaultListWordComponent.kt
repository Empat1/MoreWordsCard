package ru.empat.morewords.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import ru.empat.morewords.domain.entity.Word

class DefaultListWordComponent @AssistedInject constructor(
    private val storeFactory: ListWordStoreFactory,
    @Assisted("onBackClicked") val onBackClicked: () -> Unit,
    @Assisted("onWordClicked") val onWordClicked: (Word) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : ListWordComponent, ComponentContext by componentContext {

    val store = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ListWordStore.State>
        get() = store.stateFlow

    override fun clickBack() {
        onBackClicked()
    }

    override fun openWord(word: Word) {
        onWordClicked(word)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("onWordClicked") onWordClicked: (Word) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultListWordComponent
    }
}
