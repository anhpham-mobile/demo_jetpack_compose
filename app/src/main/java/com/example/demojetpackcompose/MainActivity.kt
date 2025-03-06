package com.example.demojetpackcompose

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demojetpackcompose.ui.theme.DemoJetpackComposeTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var isDark by remember { mutableStateOf(false) }

            DemoJetpackComposeTheme(darkTheme = isDark) {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    Column {
                        SimpleToggle {
                            isDark = it
                        }
                        NormalColumn()
//                    LazyColumnExample()
                    }
                }
            }
        }
    }
}

@Composable
fun NormalColumn() {
    Column(
        modifier = Modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        SimpleTextView()
        SimpleButton()
        SimpleTextField()
        OutlineTextField(Modifier.padding(16.dp))
        SimpleImage()
        SimpleCheckbox()
        SimpleRadioGroup()
        SimpleSlider()
        BoxExample()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoJetpackComposeTheme {
        SimpleTextView()
        SimpleButton()
        SimpleTextField()
        OutlineTextField()
        SimpleImage()
        SimpleCheckbox()
    }
}

@Composable
fun SimpleTextView() {
    Text(
        text = "Hello, Jetpack Compose!",
        fontSize = 20.sp, //Scale-independent Pixels
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(16.dp) //Density-independent Pixels
    )
}

@Composable
fun SimpleButton() {
    Button(
        onClick = { /* Handle click */ },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Click Me")
    }
}

@Composable
fun SimpleTextField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Enter your name") },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun OutlineTextField(modifier: Modifier = Modifier) {
    OutlinedTextField("",
        label = { Text("Enter your name") }, onValueChange = {}, modifier = modifier
    )
}

@Composable
fun SimpleImage() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.images),
            contentDescription = "Sample Image",
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun SimpleImageLocal(filePath: String) {
    val imageBitmap by produceState<ImageBitmap?>(initialValue = null, filePath) {
        val file = File(filePath)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            value = bitmap.asImageBitmap()
        }
    }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = "Sample Image",
            modifier = Modifier
                .size(150.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }

    Button(onClick = {}) { }
}

@Composable
fun SimpleCheckbox() {
    var checked by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text(text = if (checked) "Checked" else "Unchecked")
    }
}

@Composable
fun SimpleRadioGroup() {
    var selectedOption by remember { mutableStateOf("Option 1") }
    Column(Modifier.padding(16.dp)) {
        listOf("Option 1", "Option 2", "Option 3").forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { selectedOption = option }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun SimpleSlider() {
    var sliderValue by remember { mutableFloatStateOf(0f) }
    Column(Modifier.padding(16.dp)) {
        Text(text = "Value: ${sliderValue.toInt()}")
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..100f
        )
    }
}

@Composable
fun SimpleToggle(onChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(false) } // Lưu trạng thái bật/tắt

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onChange.invoke(it)
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = if (isChecked) "ON" else "OFF", fontSize = 20.sp)
    }
}

@Composable
fun BoxExample() {
    Box(
        modifier = Modifier
            .size(200.dp, 100.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello, Box!", color = Color.White)
    }
}

@Composable
fun LazyColumnExample() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(50) { index ->
            Text(
                text = "Item $index",
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


