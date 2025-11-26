package ru.empat.morewords.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DefaultListWordComponent @AssistedInject constructor(
    private val storeFactory: ListWordStoreFactory,
    @Assisted("onBackClicked") val onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : ListWordComponent, ComponentContext by componentContext {

    val store = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ListWordStore.State>
        get() = store.stateFlow

    override fun clickBack() {
        onBackClicked.invoke()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultListWordComponent
    }
}
