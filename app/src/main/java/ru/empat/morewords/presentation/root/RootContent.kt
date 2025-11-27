package ru.empat.morewords.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.empat.morewords.presentation.add.AddWordScreen
import ru.empat.morewords.presentation.edit.EditScreen
import ru.empat.morewords.presentation.education.EducationScreen
import ru.empat.morewords.presentation.learn.LearnCardContent
import ru.empat.morewords.presentation.list.ListWordScreen
import ru.empat.morewords.ui.theme.MoreWordsTheme

@Composable
fun RootContent(component: RootComponent) {
    MoreWordsTheme {
        Children(
            stack = component.stack
        ) {
            when (val instance = it.instance) {
                is RootComponent.Child.LearnCard -> {
                    LearnCardContent(component = instance.component)
                }

                is RootComponent.Child.Education -> {
                    EducationScreen(component = instance.component)
                }

                is RootComponent.Child.AddCard -> {
                    AddWordScreen(component = instance.component)
                }

                is RootComponent.Child.ListWord -> {
                    ListWordScreen(component = instance.component)
                }

                is RootComponent.Child.EditWord -> {
                    EditScreen(component = instance.component)
                }
            }
        }
    }
}