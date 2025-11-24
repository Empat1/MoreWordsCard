package ru.empat.morewords.presentation.learn

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.empat.morewords.R
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.ui.theme.Green
import ru.empat.morewords.ui.theme.Red


const val ROTATION = 180f

@Composable
fun LearnCardContent(component: LearnCardComponent) {
    val state by component.model.collectAsState()

    when (val currentState = state.wordState) {
        LearnCardStore.State.WordState.Error -> {}
        LearnCardStore.State.WordState.Init -> {}
        LearnCardStore.State.WordState.Loading -> {}
        is LearnCardStore.State.WordState.WordLoaded -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MyCard(
                    word = currentState.word,
                    hide = currentState.isHide,
                    onClick = {
                        component.onClick()
                    },
                    onRight = {
                        component.learn(currentState.word.wordId , true)
                    },
                    onLeft = {
                        component.learn(currentState.word.wordId , false)
                    }
                )
            }
        }
    }
}

@Composable
fun MyCard(
    word: Word,
    hide: Boolean,
    onClick: (() -> Unit),
    onRight: (() -> Unit),
    onLeft: (() -> Unit)
) {

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == StartToEnd) onRight.invoke()
            else if (it == EndToStart) onLeft.invoke()
            it != StartToEnd
        }
    )

    val stateRotationY by animateFloatAsState(
        targetValue = if (hide) 180f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromStartToEnd = !hide,
        enableDismissFromEndToStart = !hide,
        modifier = Modifier
            .padding(16.dp),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                StartToEnd -> {
                    Side(stringResource(R.string.know), Green, contentAlignment = Alignment.TopEnd)
                }

                EndToStart -> {
                    Side(stringResource(R.string.notKnow), Red, Alignment.TopStart)
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
                    onClick.invoke()
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
                        if (stateRotationY == ROTATION) {
                            rotationY = ROTATION
                        }
                    }
            ) {
                if (stateRotationY == ROTATION) {
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer {
                                    rotationY = stateRotationY
                                    cameraDistance = 12f * density
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Side(text: String, color: Color, contentAlignment: Alignment) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        Text(
            text = text,
            color = color
        )
    }
}