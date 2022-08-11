package pl.jsyty.architecturetemplate.test

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.jsyty.architecturetemplate.ui.BaseComposeFragment
import pl.jsyty.architecturetemplate.ui.theme.ArchitectureTemplateTheme

class TestFragment : BaseComposeFragment() {
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Greeting("Android")
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ArchitectureTemplateTheme {
            Greeting("Android")
        }
    }
}
