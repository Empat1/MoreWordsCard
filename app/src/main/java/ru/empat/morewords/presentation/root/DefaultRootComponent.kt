package ru.empat.morewords.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.add.DefaultAddWordComponent
import ru.empat.morewords.presentation.edit.DefaultEditWordComponent
import ru.empat.morewords.presentation.education.DefaultEducationComponent
import ru.empat.morewords.presentation.learn.DefaultLearnComponent
import ru.empat.morewords.presentation.list.DefaultListWordComponent
import ru.empat.morewords.presentation.root.RootComponent.Child.*

class DefaultRootComponent @AssistedInject constructor(
    private val learnCardComponentFactory: DefaultLearnComponent.Factory,
    private val educationComponentFactory: DefaultEducationComponent.Factory,
    private val addCardComponentFactory: DefaultAddWordComponent.Factory,
    private val listWordComponentFactory: DefaultListWordComponent.Factory,
    private val editWordComponentFactory: DefaultEditWordComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.EducationLearn,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config, componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            Config.CardLean -> {
                val component = learnCardComponentFactory.create(
                    componentContext = componentContext,
                    onBackClicked = {
                        navigation.pop()
                    }
                )
                LearnCard(component)
            }

            Config.EducationLearn -> {
                val component = educationComponentFactory.crate(
                    componentContext,
                    onShowList = {
                        navigation.push(Config.ListWord)
                    },
                    clickLearn = {
                        navigation.push(Config.CardLean)
                    },
                    addWord = {
                        navigation.push(Config.AddCard)
                    }
                )
                Education(component)
            }

            Config.AddCard -> {
                val component = addCardComponentFactory.create(
                    {
                        navigation.pop()
                    },
                    {
                        navigation.push(Config.EditWord(it))
                    },
                    componentContext
                )
                AddCard(component)
            }

            Config.ListWord -> {
                val component = listWordComponentFactory.create(
                    {
                        navigation.pop()
                    },
                    onWordClicked = { word ->
                        navigation.push(Config.EditWord(word))
                    },
                    componentContext
                )
                ListWord(component)
            }

            is Config.EditWord -> {
                val component = editWordComponentFactory.create(
                    word = config.word,
                    onBackClicked = {
                        navigation.pop()
                    },
                    componentContext
                )

                EditWord(component)
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object CardLean : Config

        @Parcelize
        data object EducationLearn : Config

        @Parcelize
        data object AddCard : Config

        @Parcelize
        data object ListWord : Config

        @Parcelize
        data class EditWord(val word: Word) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}