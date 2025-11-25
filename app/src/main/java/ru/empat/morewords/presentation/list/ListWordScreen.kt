package ru.empat.morewords.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.empat.morewords.domain.entity.Word

@Composable
fun ListWordScreen(component: ListWordComponent) {
    val state = component.model.collectAsState()

    when (val currentState = state.value) {
        ListWordStore.State.Init -> {}
        is ListWordStore.State.Loaded -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                ListWord(word = currentState.words)
            }
        }

        ListWordStore.State.Loading -> {

        }
    }
}

@Composable
private fun ListWord(word: List<Word>) {
    LazyColumn {
        items(items = word, key = { it.wordId }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp, start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = it.text)
                    Text(text = it.translate, textAlign = TextAlign.End)
                }
            }
        }
    }
}