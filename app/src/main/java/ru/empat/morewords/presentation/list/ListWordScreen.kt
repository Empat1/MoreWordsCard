package ru.empat.morewords.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.empat.morewords.R
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.ui.component.TopAppBar

@Composable
fun ListWordScreen(component: ListWordComponent) {
    val state = component.model.collectAsState()

    Scaffold(
        topBar = { Topbar { component.clickBack() } }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(all = 8.dp),
        ) {

            when (val currentState = state.value) {
                ListWordStore.State.Init -> {}
                is ListWordStore.State.Loaded -> {
                    ListWord(
                        word = currentState.words,
                        wordClick = {
                            component.openWord(it)
                        },
                        removeClick = {
                            component.remove(it)
                        })
                }

                ListWordStore.State.Loading -> {
                    //TODO add load and pagination
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Topbar(onBackClick: () -> Unit) {
    TopAppBar(
        titleText = stringResource(R.string.all_words),
        onBackClick = onBackClick
    )
}

@Composable
private fun ListWord(word: List<Word>, wordClick: (Word) -> Unit, removeClick: (Word) -> Unit) {
    LazyColumn {
        items(items = word, key = { it.wordId }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                onClick = { wordClick.invoke(it) },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = it.text,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            ),
                            fontSize = 18.sp
                        )
                        Text(
                            text = it.translate,
                            fontSize = 16.sp
                        )
                    }

                    IconButton(
                        onClick = { removeClick(it) }) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}