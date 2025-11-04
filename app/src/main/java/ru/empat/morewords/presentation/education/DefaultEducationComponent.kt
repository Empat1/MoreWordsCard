package ru.empat.morewords.presentation.education

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.componentScope

class DefaultEducationComponent @AssistedInject constructor(
    private val educationStoreFactory: EducationStoreFactory,
    @Assisted("clickLearn") private val clickLearn: () -> Unit,
    @Assisted("onShowList") private val onShowList: () -> Unit,
    @Assisted("addWord") private val addWord: (Word) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : EducationComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { educationStoreFactory.create() }
    val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect{
                when(it) {
                    EducationStore.Label.ClickAddCard -> TODO()
                    EducationStore.Label.ClickGetList -> TODO()
                    EducationStore.Label.ClickLearn -> {
                        clickLearn()
                    }
                }
            }
        }
    }

    override val model: StateFlow<EducationStore.State> = store.stateFlow

    override fun onClickEducation() {
        store.accept(EducationStore.Intent.ClickLearn)
    }

    override fun onShowList() {
        store.accept(EducationStore.Intent.ClickGetList)
    }

    override fun addWord(card: Word) {
        store.accept(EducationStore.Intent.ClickAddCard)
    }

    @AssistedFactory
    interface Factory {
        fun crate(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("clickLearn") clickLearn: () -> Unit,
            @Assisted("onShowList") onShowList: () -> Unit,
            @Assisted("addWord") addWord: (Word) -> Unit,
        ): DefaultEducationComponent
    }
}