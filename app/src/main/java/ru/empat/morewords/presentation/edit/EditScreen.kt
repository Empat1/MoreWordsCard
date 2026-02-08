package ru.empat.morewords.presentation.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.empat.morewords.R
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.ui.component.TopAppBar

@Composable
fun EditScreen(component: EditCardComponent) {

    Scaffold(
        topBar = { Topbar { component.onBackClick() } }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 8.dp, end = 8.dp),
            contentAlignment = Alignment.Center
        ) {

            val state = component.model.collectAsState()
            val word = state.value.word

            WordScreen(
                word = word,
                editClick = { text, translate ->
                    component.editClick(text, translate)
                },
                removeClick = {
                    component.removeClick(it)
                }
            )
        }
    }
}

@Composable
private fun WordScreen(
    word: Word,
    editClick: (String, String) -> Unit,
    removeClick: (Long) -> Unit
) {
    val text = remember { mutableStateOf(word.text) }
    val translate = remember { mutableStateOf(word.translate) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.padding(8.dp),
            value = text.value,
            onValueChange = { text.value = it }
        )

        TextField(
            modifier = Modifier.padding(8.dp),
            value = translate.value,
            onValueChange = { translate.value = it }
        )

        Button(
            onClick = {
                editClick.invoke(text.value, translate.value)

            }
        ) {
            Text(stringResource(R.string.save))
        }

        Button(
            onClick = {
                removeClick.invoke(word.wordId)
            }
        ) {
            Text(stringResource(R.string.remove))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Topbar(onBackClick: () -> Unit) {
    TopAppBar(
        titleText = stringResource(R.string.edit_card),
        onBackClick = onBackClick
    )
}