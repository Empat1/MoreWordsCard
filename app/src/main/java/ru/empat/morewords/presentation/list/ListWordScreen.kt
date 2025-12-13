package ru.empat.morewords.presentation.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.empat.morewords.R
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.ui.component.TopAppBar

@Composable
fun ListWordScreen(component: ListWordComponent) {
    val state = component.model.collectAsState()

    Scaffold(
        topBar = { Topbar({ component.clickBack() }) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 8.dp, end = 8.dp),
        ) {

            when (val currentState = state.value) {
                ListWordStore.State.Init -> {}
                is ListWordStore.State.Loaded -> {
                    ListWord(word = currentState.words){
                        component.openWord(it)
                    }
                }

                ListWordStore.State.Loading -> {

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
private fun ListWord(word: List<Word>, wordClick: (Word) -> Unit) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    LazyColumn {
        items(items = word, key = { it.wordId }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha),
                onClick = { wordClick.invoke(it) },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp, start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = it.text)
                    Text(text = it.translate, textAlign = TextAlign.End)
                }
            }
        }
    }
}