package ru.empat.morewords.presentation.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.empat.morewords.R
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.presentation.edit.WordFiled
import ru.empat.morewords.ui.component.TopAppBar


@Composable
fun AddWordScreen(component: AddWordComponent) {
    Scaffold(
        topBar = { Topbar { component.onBackClick() } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            val error = remember { mutableStateOf<AddWordStore.State.Input.Error?>(null) }

            WordScreen(
                error = error.value,
                addClick = { text, translate ->
                    component.onClickAdd(text, translate)
                },
                changeWord = { component.onChangeWord(it) },
                editWord = { component.onEditWord(it) }
            )

            when (val state = component.model.collectAsState().value) {
                AddWordStore.State.Init -> {}
                AddWordStore.State.Success -> {}
                is AddWordStore.State.Error -> {}
                AddWordStore.State.Input.Conform -> {
                    error.value = null
                }

                is AddWordStore.State.Input.Error -> {
                    error.value = state
                }
            }
        }
    }
}

@Composable
private fun WordScreen(
    error: AddWordStore.State.Input.Error?,
    addClick: (String, String) -> Unit,
    changeWord: (String) -> Unit,
    editWord: (Word) -> Unit
) {
    val text = rememberSaveable { mutableStateOf("") }
    val translate = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WordFiled(text.value, stringResource(R.string.Word)) {
            text.value = it
            changeWord.invoke(it)
        }

        WordFiled(translate.value, stringResource(R.string.Translate)) {
            translate.value = it
        }

        var saveEnable = true


        if (error != null) {
            saveEnable = false
            ShowError(error) {
                editWord.invoke(it)
            }
        }

        Button(
            enabled = saveEnable,
            onClick = {
                addClick.invoke(text.value, translate.value)
                text.value = ""
                translate.value = ""
            }
        ) {
            Text(stringResource(R.string.save))
        }
    }
}

@Composable
private fun ShowError(
    error: AddWordStore.State.Input.Error,
    editWord: (Word) -> Unit
) {
    when (error) {
        is AddWordStore.State.Input.Error.HasWord -> {
            val text = "${stringResource(R.string.DublicateCard)} ${error.word.text}"
            Text(text, color = MaterialTheme.colorScheme.error)
            Text(
                text = stringResource(R.string.ChangeOriginal),
                color = MaterialTheme.colorScheme.error,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    editWord.invoke(error.word)
                },
            )
        }

        is AddWordStore.State.Input.Error.Validation -> {
            val text = stringResource(error.text)
            Text(text, color = MaterialTheme.colorScheme.error)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Topbar(onBackClick: () -> Unit) {
    TopAppBar(
        titleText = stringResource(R.string.add_word),
        onBackClick = onBackClick
    )
}