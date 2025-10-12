package ru.empat.morewords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.empat.morewords.ui.theme.MoreWordsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoreWordsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ContactRow(
//                        Contact("Test", ""),
//                        modifier = Modifier.padding(innerPadding)
//                    )

//                    CustomBox(
//                        modifier = Modifier.padding(innerPadding),
//                    ){
//                        Text("1")
//                        Text("2")
//                        Text("3")
//                        Text("4")
//                    }


//                    CrossfadeExample(Modifier.padding(innerPadding))
                    CardScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoreWordsTheme {
        ContactRow(Contact("Test", ""))
    }
}

@Immutable
class Contact(var name: String, val number: String)

@Composable
fun ContactRow(contact: Contact, modifier: Modifier = Modifier) {
    Box {
        var paddingBottom: Float by remember { mutableStateOf(0f) }
        var paddingStart: Float by remember { mutableStateOf(0f) }

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .onSizeChanged { size ->
                    paddingBottom = size.height.toFloat()
                    paddingStart = size.width.toFloat()
                },
            text = "Информация"
        )
        Icon(
            modifier = Modifier
                .padding(start = paddingStart.toInt().dp, bottom = paddingBottom.toInt().dp)
                .align(Alignment.BottomStart),
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            tint = Color.Gray,
        )
    }
}


@Composable
fun CustomBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        // Измеряем дочерние элементы
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        // Определяем размеры родительского контейнера
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Устанавливаем размер текущего контейнера
        layout(width, height) {
            // Вычисляем позицию для всех дочерних элементов на основе contentAlignment
            placeables.forEach { placeable ->
                val position = when (contentAlignment) {
                    Alignment.TopEnd -> IntOffset(width - placeable.width, 0) // в правом верхнем углу
                    Alignment.Center -> IntOffset((width - placeable.width) / 2, 0) // верх центр
                    else -> IntOffset(0, 0) // во всех остальных случаях в левом верхнем углу
                }

                // Размещаем дочерние элементы
                placeable.place(position.x, position.y)
            }
        }
    }
}

@Composable
fun A(){
    var isDarkTheme by remember { mutableStateOf(false) }
// Анимация цвета фона
    val backgroundColor by animateColorAsState(
        targetValue = if (isDarkTheme) Color.Black else Color.White,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize(),

        contentAlignment = Alignment.Center
    ) {
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { isDarkTheme = it },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}


@Composable
fun CrossfadeExample(modifier: Modifier = Modifier) {
    var stateNumber by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Выберите состояние экрана:")
        Row {
            Button(onClick = { stateNumber = 1 }) {
                Text(text = "Контент")
            }
            Button(onClick = { stateNumber = 2 }) {
                Text(text = "Загрузка")
            }
            Button(onClick = { stateNumber = 3 }) {
                Text(text = "Ошибка")
            }
        }
        Crossfade(
            targetState = stateNumber,
            animationSpec = tween(durationMillis = 600)
        ) { state ->
            when (state) {
                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Green)
                    ) {
                        Text("CONTENT", modifier = Modifier.align(Alignment.Center))
                    }
                }

                2 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                3 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                    ) {
                        Text("SORRY, ERROR", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Composable
fun DrawContact(contact: Contact){
    Text(contact.name)
}