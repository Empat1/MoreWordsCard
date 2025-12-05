package ru.empat.morewords.presentation.learn

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.empat.morewords.domain.entity.Word
import kotlin.math.abs
import kotlin.random.Random


const val ROTATION = 180f
const val ALPHA_ANIMATION_DURATION = 400
const val ROTATION_ANIMATION_DURATION = 400
const val SWIPE_ANIMATION_DURATION = 600
const val Y_TRANSACTION_TIME = 300

const val WIGHT_CARD = 0.65f
const val HEIGHT_CARD = 0.5f


const val Z_TOP = 10f
const val Z_BOTTOM = 5f

const val DETECT_DRAG_LONG = 100

@Composable
fun LearnCardContent(component: LearnCardComponent) {
    val state by component.model.collectAsState()

    when (val currentState = state.wordState) {
        LearnCardStore.State.WordState.Error -> {}
        LearnCardStore.State.WordState.Init -> {}
        LearnCardStore.State.WordState.Loading -> {}
        is LearnCardStore.State.WordState.WordLoaded -> {
            LoadedWord(component, currentState)
        }
    }
}

@Composable
private fun LoadedWord(
    component: LearnCardComponent,
    state: LearnCardStore.State.WordState.WordLoaded
) {
    val animationStep: MutableState<Animation> =
        remember(state.word) { mutableStateOf(Animation.Init) }

    val wordId = state.word.wordId

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MyCard(
            word = state.word,
            hide = state.isHide,
            onClick = {
                component.onClick()
            },
            animationStep = animationStep.value,
            onRight = {
                animationStep.value = Animation.Right.Swipe
            },
            onLeft = {
                animationStep.value = Animation.Left.SwipeLeft
            },
            onAnimationStepComplete = {

                Log.d("Animation", "onAnimationStepComplete = ${animationStep}")

                if (it != animationStep.value) return@MyCard

                when (it) {
                    Animation.Init -> {
                    }

                    Animation.Left.SwipeLeft -> {
                        animationStep.value = Animation.Left.CorrectY
                    }

                    Animation.Left.CorrectY -> {
                        animationStep.value = Animation.Left.SwipeRight
                    }

                    Animation.Left.SwipeRight -> {
                        component.learn(wordId, false)
                        animationStep.value = Animation.Init
                    }


                    Animation.Right.Swipe -> {
                        component.learn(wordId, true)
                        animationStep.value = Animation.Init
                    }


                }
            }
        )
        BackgroundCards()
    }
}

@Composable
fun BackgroundCards() {
    repeat(5) {
        Card(
            modifier = Modifier
                .fillMaxWidth(WIGHT_CARD)
                .fillMaxHeight(HEIGHT_CARD)
                .zIndex(Z_BOTTOM)
                .rotate(Random.nextInt(from = -10, until = 10).toFloat()),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {}
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(WIGHT_CARD)
            .fillMaxHeight(HEIGHT_CARD)
            .zIndex(Z_BOTTOM + 1),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {}
}

@Composable
fun MyCard(
    word: Word,
    hide: Boolean,
    animationStep: Animation,
    onClick: (() -> Unit),
    onRight: (() -> Unit),
    onLeft: (() -> Unit),
    onAnimationStepComplete: (Animation) -> Unit
) {
    val isInit = animationStep is Animation.Init

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenWidthPx = with(LocalDensity.current) {
        screenWidth.dp.toPx()
    }

    var endOffsetX by remember(word) { mutableFloatStateOf(0f) }
    var endOffsetY by remember(word) { mutableFloatStateOf(0f) }
    var dragEnable = remember(word) { true }

    val stateRotationY by animateFloatAsState(
        targetValue = if (hide) 180f else 0f,
        animationSpec = if (hide) snap() else tween(durationMillis = ROTATION_ANIMATION_DURATION),
    )

    val alphaAnimation by animateFloatAsState(
        targetValue = if (animationStep is Animation.Right.Swipe) 0f else 1f,
        animationSpec = if (isInit) tween(durationMillis = ALPHA_ANIMATION_DURATION) else snap(),
    )

    val animationX by animateFloatAsState(
        targetValue = when (animationStep) {
            is Animation.Init -> {
                if (hide) 0f
                else endOffsetX
            }

            is Animation.Right.Swipe -> endOffsetX + screenWidthPx
            is Animation.Left.SwipeLeft -> {
                endOffsetX - screenWidthPx
            }

            is Animation.Left.SwipeRight -> 0f

            else -> endOffsetX
        },
        animationSpec = when (animationStep) {
            is Animation.Left.SwipeLeft -> tween(durationMillis = SWIPE_ANIMATION_DURATION, delayMillis = 100)
            is Animation.Left.SwipeRight -> tween(SWIPE_ANIMATION_DURATION, easing = FastOutSlowInEasing)
            is Animation.Right.Swipe -> tween(SWIPE_ANIMATION_DURATION)
            else -> snap()
        },
        finishedListener = {
            when (animationStep) {
                is Animation.Left.CorrectY -> {}

                is Animation.Left.SwipeRight -> {
                    endOffsetX = 0f
                    endOffsetY = 0f
                    onAnimationStepComplete.invoke(animationStep)
                }

                else -> {
                    onAnimationStepComplete.invoke(animationStep)
                }
            }
        }
    )

    val animationY by animateFloatAsState(
        targetValue = when (animationStep) {
            is Animation.Left.CorrectY,
            is Animation.Left.SwipeRight -> {
                0f
            }

            else -> endOffsetY
        },
        animationSpec = when (animationStep) {
            is Animation.Left.CorrectY -> tween(Y_TRANSACTION_TIME)
            else -> snap()
        },
        finishedListener = {
            if (animationStep is Animation.Left.CorrectY)
                onAnimationStepComplete.invoke(animationStep)
        }
    )

    val zAnimationRotation by animateFloatAsState(
        targetValue = when (animationStep) {
            is Animation.Left.SwipeRight,
            is Animation.Left.CorrectY -> Z_BOTTOM - 1

            else -> Z_TOP
        },
        animationSpec = when (animationStep) {
            is Animation.Left.SwipeRight,
            is Animation.Left.CorrectY -> tween(SWIPE_ANIMATION_DURATION)

            else -> snap()
        }

    )

    Card(
        modifier = Modifier
            .fillMaxWidth(WIGHT_CARD)
            .fillMaxHeight(HEIGHT_CARD)
            .clickable {
                if (animationStep.isAnimate) return@clickable
                onClick.invoke()
            }
            .zIndex(zAnimationRotation)
            .graphicsLayer {
                translationX = animationX
                translationY = animationY

                rotationY = stateRotationY
                cameraDistance = 12f * density
                alpha = alphaAnimation
            }
            .pointerInput(hide) {
                detectDragGestures(
                    onDragEnd = {
                        when (endDragAnimation(endOffsetX)) {
                            EndDragSwipe.Left -> {
                                dragEnable = false
                                onLeft()
                            }

                            EndDragSwipe.Right -> {
                                dragEnable = false
                                onRight()
                            }

                            EndDragSwipe.No -> {
                                endOffsetX = 0f
                                endOffsetY = 0f
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        if (!hide && dragEnable && !animationStep.isAnimate) {
                            endOffsetX += dragAmount.x
                            endOffsetY += dragAmount.y
                        }
                        change.consume()
                    }
                )
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        WordCard(stateRotationY, word)
    }

}
@Composable
fun WordCard(stateRotationY: Float, word: Word) {
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

fun endDragAnimation(endOffsetX: Float): EndDragSwipe {
    if (abs(endOffsetX) < DETECT_DRAG_LONG)
        return EndDragSwipe.No

    return if (endOffsetX > 0) {
        EndDragSwipe.Right
    } else {
        EndDragSwipe.Left
    }
}

sealed interface EndDragSwipe {
    data object Right : EndDragSwipe
    data object Left : EndDragSwipe
    data object No : EndDragSwipe
}


@Stable
sealed class Animation(val isAnimate: Boolean) {

    @Stable
    data object Init : Animation(false)

    @Stable
    sealed class Right(isAnimate: Boolean) : Animation(isAnimate) {
        @Stable
        data object Swipe : Right(true)
    }

    @Stable
    sealed class Left(isAnimate: Boolean) : Animation(isAnimate) {
        @Stable
        data object SwipeLeft : Right(true)

        @Stable
        data object CorrectY : Right(true)

        @Stable
        data object SwipeRight : Right(true)
    }
}