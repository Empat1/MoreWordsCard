package ru.empat.morewords.presentation.education

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import ru.empat.morewords.R

@Composable
fun EducationScreen(component: EducationComponent) {
    val state by component.model.collectAsState()

    Scaffold(
        containerColor = Color.Transparent
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
        ) {
            GreetingSection()
            when (val current = state.statisticState) {
                is EducationStore.State.StatisticState.Error -> {
                }

                EducationStore.State.StatisticState.Init -> {}
                EducationStore.State.StatisticState.Loading -> {}
                is EducationStore.State.StatisticState.Loaded -> {
                    StatisticItems(current)

                }
            }

            EducationAction(component)
        }
    }
}

@Composable
fun EducationAction(component: EducationComponent) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

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
                    modifier = modifier, verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { component.onClickEducation() }) {
                        Text(text = stringResource(R.string.start))
                    }

                    IconButton(
                        onClick = { component.addWord() },
                        modifier = Modifier.clip(CircleShape),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add, contentDescription = "Add"
                        )
                    }
                }

                Button(
                    modifier = modifier, onClick = {
                        component.onShowList()
                    }) {
                    Text(text = stringResource(R.string.all_words))
                }
            }
        }
    }
}

@Composable
fun StatisticItems(state: EducationStore.State.StatisticState.Loaded) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(intrinsicSize = IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(4.dp)


        StatisticBox(
            modifier = modifier,
            count = state.deyStreak,
            description = stringResource(R.string.day_streak),
            image = R.drawable.ic_fire
        )
        StatisticBox(
            modifier = modifier,
            count = state.completeWord,
            description = stringResource(R.string.know),
            image = R.drawable.outline_book_2_24
        )
        StatisticBox(
            modifier = modifier,
            count = state.wordForLearn,
            description = stringResource(R.string.learn),
            image = R.drawable.word_study
        )
    }
}

@Composable
fun GreetingSection() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(
                    width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape
                )
        )

        Column(Modifier.padding(start = 8.dp)) {
            Text(
                text = "Good morning", color = Color.Gray
            )
            Text(
                text = "My friend", fontSize = 24.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StatisticBox(modifier: Modifier, count: Int, description: String, image: Int) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp),
                    painter = painterResource(id = image),
                    contentDescription = description
                )

                Text(
                    text = description,
                    fontSize = 14.sp,
                    lineHeight = 1.em
                )
            }

            Row(Modifier.padding(top = 2.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "$count",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}