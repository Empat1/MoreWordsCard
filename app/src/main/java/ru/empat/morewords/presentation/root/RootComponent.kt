package ru.empat.morewords.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.empat.morewords.presentation.add.AddWordComponent
import ru.empat.morewords.presentation.edit.DefaultEditWordComponent
import ru.empat.morewords.presentation.education.EducationComponent
import ru.empat.morewords.presentation.learn.LearnCardComponent
import ru.empat.morewords.presentation.list.ListWordComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class LearnCard(val component: LearnCardComponent) : Child
        data class Education(val component: EducationComponent) : Child

        data class AddCard(val component: AddWordComponent) : Child

        data class ListWord(val component: ListWordComponent) : Child

        data class EditWord(val component: DefaultEditWordComponent) : Child
    }
}