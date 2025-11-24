package ru.empat.morewords.presentation.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.empat.morewords.R


@Composable
fun AddWordScreen(component: AddWordComponent) {
    val state by component.model.collectAsState()

    val text = remember { mutableStateOf("") }
    val translate = remember { mutableStateOf("") }

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
                component.onClickAdd(text.value, translate.value)
            }
        ) {
            Text(stringResource(R.string.save))
        }
    }
}

data class SaveWord(
    var text: String,
    var translate: String
)