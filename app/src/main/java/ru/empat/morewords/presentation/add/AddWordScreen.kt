package ru.empat.morewords.presentation.add

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.empat.morewords.R
import ru.empat.morewords.presentation.edit.WordFiled
import ru.empat.morewords.ui.component.TopAppBar


@Composable
fun AddWordScreen(component: AddWordComponent) {
    Scaffold(
        topBar = { Topbar { component.onBackClick() } }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 8.dp, end = 8.dp)
                .fitInside(WindowInsetsRulers.Ime.current),
            contentAlignment = Alignment.Center
        ) {

            WordScreen(
                addClick = { text, translate ->
                    component.onClickAdd(text, translate)

                }
            )
        }
    }
}

@Composable
private fun WordScreen(
    addClick: (String, String) -> Unit
) {
    val text = rememberSaveable { mutableStateOf("") }
    val translate = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WordFiled(text.value, stringResource(R.string.Word)){
            text.value = it
        }


        WordFiled(translate.value, stringResource(R.string.Translate)){
            translate.value = it
        }

        Button(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Topbar(onBackClick: () -> Unit) {
    TopAppBar(
        titleText = stringResource(R.string.add_word),
        onBackClick = onBackClick
    )
}