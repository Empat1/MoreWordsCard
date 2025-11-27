package ru.empat.morewords.presentation.edit

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

class DefaultEditWordComponent @AssistedInject constructor(
    private val storeFactory: EditCardStoreFactory,
    @Assisted("word") private val word: Word,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : EditCardComponent, ComponentContext by componentContext {

    val store = instanceKeeper.getStore { storeFactory.create(word) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<EditCardStore.State> = store.stateFlow

    val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it){
                    EditCardStore.Label.NavigationBack -> {
                        onBackClicked()
                    }
                    EditCardStore.Label.SaveWord -> {}
                }
            }
        }
    }

    override fun onBackClick() {
        store.accept(EditCardStore.Intent.ClickBack)
    }

    override fun editClick(text: String, translate: String) {
        store.accept(EditCardStore.Intent.EditWord(text, translate))
    }

    override fun removeClick(wordId: Long) {
        store.accept(EditCardStore.Intent.RemoveWord(word.wordId))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("word") word: Word,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ) : DefaultEditWordComponent
    }
}