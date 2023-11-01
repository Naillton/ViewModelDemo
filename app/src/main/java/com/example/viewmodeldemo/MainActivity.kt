package com.example.viewmodeldemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodeldemo.ui.theme.ViewModelDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
private fun ScreenSetup(model: DemoViewModel = viewModel()) {
    // usando o observeAsState para observar os dados mutaveis no liveData
    // val result by model.result.observeAsState("")
    // val isFahrenheit by model.isFahrenheit.observeAsState(true)
    MainScreen(
        isFahrenheit = model.isFahrenheit,
        result = model.result,
        convert = { model.convert(it) },
        switch =  { model.switchFahrenheit() }
    )
}

@Composable
fun MainScreen(isFahrenheit: Boolean, result: String, convert: (String) -> Unit, switch: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        var textState by rememberSaveable { mutableStateOf("") }
        val onChange = { it: String ->
            textState = it
        }

        Text(
            "Tempoerature Converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineSmall
            )

        InputRow(
            isFahrenheit = isFahrenheit,
            textState = textState,
            convert = onChange,
            switch = switch
        )

        Text(result,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = { convert(textState) }) {
            Text("Convert Temperature")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(isFahrenheit: Boolean, textState: String, convert: (String) -> Unit, switch: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // switch para trocar o estado do isFahrenheit
        Switch(checked = isFahrenheit, onCheckedChange = { switch() })

        // outlinedText usado para definir um valor de temperatura
        OutlinedTextField(
            value = textState,
            onValueChange = { convert(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            label = { Text(text = "Enter Temperature")},
            modifier = Modifier.padding(10.dp).width(200.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            ),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_ac_unit_24),
                    contentDescription = "gelin",
                    modifier = Modifier.size(40.dp))
            }
        )

        // usadno crossfade para criar uma animacao no texto que sera visivel quando o fahreneheit for true ℉
        // e quando for false celsius ℃
        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(2000),
            label = ""
        ) {
                when(it) {
                    true -> Text(
                        "\u2109", style =
                        MaterialTheme.typography.headlineSmall
                    )
                    false -> Text(
                        "\u2103", style =
                        MaterialTheme.typography.headlineSmall
                    )
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ViewModelDemoTheme {
        ScreenSetup()
    }
}