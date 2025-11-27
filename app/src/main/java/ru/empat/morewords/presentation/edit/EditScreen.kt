package ru.empat.morewords.presentation.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.empat.morewords.R

@Composable
fun EditScreen(component: EditCardComponent){

    val state = component.model.collectAsState()
    val word = state.value.word
    val text = remember { mutableStateOf(word.text) }
    val translate = remember { mutableStateOf(word.translate) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = text.value,
            onValueChange = { text.value = it }
        )

        TextField(
            value = translate.value,
            onValueChange = { translate.value = it }
        )

        Button(
            onClick = {
                component.editClick(text.value, translate.value)
            }
        ) {
            Text(stringResource(R.string.save))
        }

        Button(
            onClick = {
                component.removeClick(state.value.word.wordId)
            }
        ) {
            Text(stringResource(R.string.remove))
        }
    }
}