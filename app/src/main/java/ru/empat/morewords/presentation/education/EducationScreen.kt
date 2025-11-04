package ru.empat.morewords.presentation.education

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.empat.morewords.R
import ru.empat.morewords.domain.entity.Word
import androidx.compose.runtime.getValue

@Composable
fun EducationScreen(component: EducationComponent) {
    val state by component.model.collectAsState()
    when (val currentState = state) {
        is EducationStore.State.Error -> {}
        EducationStore.State.Init -> {}
        EducationStore.State.Loading -> {}
        is EducationStore.State.Loaded -> {
            Loaded(component, currentState)
        }
    }
}

@Composable
fun Loaded(component: EducationComponent, state: EducationStore.State.Loaded) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StatisticItems(state)

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            val modifier = Modifier.fillMaxWidth().padding(12.dp)

            Column(verticalArrangement = Arrangement.Bottom) {
                Button(
                    modifier = modifier,
                    onClick = { component.onClickEducation() }
                ) {
                    Text(text = stringResource(R.string.start))
                }

                Button(
                    modifier = modifier,
                    onClick = { component.onShowList() }
                ) {
                    Text(text = stringResource(R.string.showList))
                }

                Button(
                    modifier = modifier,
                    onClick = { component.addWord(Word(1, 1, "", "")) }
                ) {
                    Text(text = stringResource(R.string.addCard))
                }
            }
        }
    }
}

@Composable
fun StatisticItems (state : EducationStore.State.Loaded){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(12.dp)

        StatisticBox(
            modifier = modifier,
            count = state.wordForLearn,
            description = stringResource(R.string.learn),
            color = Color.Green
        )
        StatisticBox(
            modifier = modifier,
            count = state.wordForRepeat,
            description = stringResource(R.string.know),
            color = Color.Blue
        )
        StatisticBox(
            modifier = modifier,
            count = state.completeWord,
            description = stringResource(R.string.learned),
            color = Color.LightGray
        )
    }
}

@Composable
fun StatisticBox(modifier: Modifier, count: Int, description: String, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(BorderStroke(2.dp, color), shape = RoundedCornerShape(12.dp))
            .aspectRatio(1f)
    ) {
        Column {
            val textModifier = Modifier.fillMaxWidth()
            Text(modifier = textModifier, text = "$count", textAlign = TextAlign.Center)
            Text(modifier = textModifier, text = description, textAlign = TextAlign.Center)
        }
    }
}