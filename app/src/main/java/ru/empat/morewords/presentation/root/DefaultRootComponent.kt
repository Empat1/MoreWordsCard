package ru.empat.morewords.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import kotlinx.parcelize.Parcelize

class DefaultRootComponent : RootComponent{

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