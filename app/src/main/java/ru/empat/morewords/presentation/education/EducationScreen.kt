package ru.empat.morewords.presentation.education

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.empat.morewords.R
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.ui.component.TopAppBar

@Composable
fun EducationScreen(component: EducationComponent) {
    val state by component.model.collectAsState()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { Toolbar(state.language) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            when (val current = state.statisticState) {
                is EducationStore.State.StatisticState.Error -> {
                }

                EducationStore.State.StatisticState.Init -> {}
                EducationStore.State.StatisticState.Loading -> {}
                is EducationStore.State.StatisticState.Loaded -> {
                    Loaded(component, current)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(language: List<Language>) {
    TopAppBar(
        language.firstOrNull()?.name ?: ""
    )
}

@Composable
fun Loaded(component: EducationComponent, state: EducationStore.State.StatisticState.Loaded) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        StatisticItems(state)

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

            Column(
                verticalArrangement = Arrangement.Bottom
            ) {

                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { component.onClickEducation() }
                    ) {
                        Text(text = stringResource(R.string.start))
                    }

                    IconButton(
                        onClick = { component.addWord() },
                        modifier = Modifier
                            .clip(CircleShape),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )
                    }
                }

                Button(
                    modifier = modifier,
                    onClick = {
                        component.onShowList()
                    }
                ) {
                    Text(text = stringResource(R.string.all_words))
                }
            }
        }
    }
}

@Composable
fun StatisticItems(state: EducationStore.State.StatisticState.Loaded) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(4.dp)

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
            color = Color.Red
        )
    }
}

@Composable
fun StatisticBox(modifier: Modifier, count: Int, description: String, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .aspectRatio(1f)
    ) {
        Column {
            val textModifier = Modifier.fillMaxWidth()
            Text(
                modifier = textModifier,
                text = "$count",
                textAlign = TextAlign.Center,
                color = color
            )
            Text(
                modifier = textModifier,
                text = description,
                textAlign = TextAlign.Center,
                color = color
            )
        }
    }
}