package ru.empat.morewords.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.education.DefaultEducationComponent
import ru.empat.morewords.presentation.learn.DefaultLearnComponent
import ru.empat.morewords.presentation.root.RootComponent.Child.*

class DefaultRootComponent @AssistedInject constructor(
    private val learnCardComponentFactory: DefaultLearnComponent.Factory,
    private val educationComponentFactory: DefaultEducationComponent.Factory,
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
                    word = Word(2, 2, "2", "2"),
                    {},
                    componentContext
                )
                LearnCard(component)
            }

            Config.EducationLearn -> {
                val component = educationComponentFactory.crate(
                    componentContext,
                    onShowList = {
                        println()
                    },
                    clickLearn = {
                        navigation.push(Config.CardLean)
                    },
                    addWord = {
                        println()
                    }
                )
                Education(component)
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object CardLean : Config

        @Parcelize
        data object EducationLearn : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}