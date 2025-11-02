package ru.empat.morewords.presentation.root

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.empat.morewords.presentation.learn.LearnCardComponent

interface RootComponent{

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class LearnCard(val component: LearnCardComponent) : RootComponent.Child
    }
}