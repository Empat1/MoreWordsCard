package ru.empat.morewords.presentation.add

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DefaultAddWordComponent @AssistedInject constructor(
    private val addWordStoreFactory: AddWordStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext
) : AddWordComponent, ComponentContext by componentContext {


    val store = instanceKeeper.getStore { addWordStoreFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<AddWordStore.State>
        get() = store.stateFlow

    override fun onClickAdd(text: String, translate: String) {
        store.accept(AddWordStore.Intent.SaveWord(text, translate))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultAddWordComponent
    }
}