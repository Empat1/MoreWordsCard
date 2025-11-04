package ru.empat.morewords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import ru.empat.morewords.presentation.root.DefaultRootComponent
import ru.empat.morewords.presentation.root.RootContent
import ru.empat.morewords.ui.theme.MoreWordsTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as MoreWordsApp).applicationComponent.inject(this)

        enableEdgeToEdge()
        setContent {
            RootContent(component = rootComponentFactory.create(defaultComponentContext()))

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
        Greeting("Android")
    }
}