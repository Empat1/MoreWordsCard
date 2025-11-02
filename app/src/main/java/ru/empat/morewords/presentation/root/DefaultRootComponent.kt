package ru.empat.morewords.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.learn.DefaultLearnComponent

class DefaultRootComponent @AssistedInject constructor(
    private val learnCardComponentFactory : DefaultLearnComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext{

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.CardLean,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config, componentContext: ComponentContext
    ): RootComponent.Child {
        return when(config){
            Config.CardLean -> {
                val component = learnCardComponentFactory.create(
                    word = Word(2, 2, "2" , "2"),
                    {},
                    componentContext
                )
                RootComponent.Child.LearnCard(component)
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object CardLean : Config
    }
    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}