package ru.empat.morewords.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.empat.morewords.ui.theme.MoreWordsTheme

@Composable
fun RootContent(component: RootComponent) {
    MoreWordsTheme{
        Children(
            stack = component.stack
        ){

        }
    }
}