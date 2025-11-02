package ru.empat.morewords

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.ui.theme.Green
import ru.empat.morewords.ui.theme.Red

@Composable
fun CardScreen(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MyCard(
            modifier = modifier,
            word = Word(1, 1, "Word", "Слово")
        )
    }
}

@Composable
fun MyCard(
    modifier: Modifier,
    word: Word,
    onRight: (() -> Unit)? = null,
    onLeft: (() -> Unit)? = null
) {

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == StartToEnd) onRight
            else if (it == EndToStart) onLeft
            it != StartToEnd
        }
    )

    var hideHint by remember { mutableStateOf(false) }

    var hide by rememberSaveable { mutableStateOf(false) }

    val stateRotationY by animateFloatAsState(
        targetValue = if (hide) 180f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromStartToEnd = hide,
        enableDismissFromEndToStart = hide,
        modifier = modifier
            .padding(16.dp),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                StartToEnd -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Знаю",
                            color = Green
                        )
                    }
                }
                EndToStart -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Text(
                            text = "Не знаю",
                            color = Red
                        )
                    }
                }
                Settled -> {}
            }
        }
    ) {
        Card(
            modifier = Modifier
                .size(320.dp, 480.dp)
                .graphicsLayer {
                    rotationY = stateRotationY
                    cameraDistance = 12f * density
                }
                .clickable {
                    hide = true
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (stateRotationY == 180f) {
                            rotationY = 180f
                        }
                    }
            ) {
                if (stateRotationY == 180f) {
                    Text(
                        text = word.translate,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {

                    Column {
                        Text(
                            text = word.text,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

