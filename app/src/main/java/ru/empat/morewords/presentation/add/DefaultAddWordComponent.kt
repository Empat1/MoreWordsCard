package ru.empat.morewords.presentation.add

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.componentScope

class DefaultAddWordComponent @AssistedInject constructor(
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("onEditWord") private val onEditWord: (Word) -> Unit,
    private val addWordStoreFactory: AddWordStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext
) : AddWordComponent, ComponentContext by componentContext {


    val store = instanceKeeper.getStore { addWordStoreFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<AddWordStore.State>
        get() = store.stateFlow

    val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    AddWordStore.Label.NavigationBack -> onBackClicked()
                }
            }
        }
    }

    override fun onClickAdd(text: String, translate: String) {
        store.accept(AddWordStore.Intent.SaveWord(text, translate))
    }

    override fun onChangeWord(text: String) {
        store.accept(AddWordStore.Intent.InputWord(text, ""))
    }

    override fun onEditWord(word: Word) {
        onEditWord.invoke(word)
    }

    override fun onBackClick() {
        store.accept(AddWordStore.Intent.ClickBack)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("onEditWord") onEditWord: (Word) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultAddWordComponent
    }
}