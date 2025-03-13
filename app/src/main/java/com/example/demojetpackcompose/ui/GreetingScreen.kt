package com.example.demojetpackcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demojetpackcompose.enums.Gender
import com.example.demojetpackcompose.enums.GreetingMessage

@Composable
fun GreetingScreen(navController: NavController) {
    var gender by remember { mutableStateOf(Gender.FEMALE.name) }
    Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
        Column {
            SimpleGenderGroup {
                gender = it
            }
            GreetingMar8(gender)
        }
    }
}

@Composable
fun SimpleGenderGroup(onChangeGender: (String) -> Unit) {
    val genders = Gender.entries.map { it.name }
    var selectedOption by remember { mutableStateOf(genders.first()) }

    Column(Modifier.padding(16.dp)) {
        genders.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = {
                        selectedOption = option
                        onChangeGender.invoke(option)
                    }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun GreetingMar8(gender: String) {
    if (gender == Gender.OTHER.name){
        Text("Nothing")
        return
    }

    Column {
        Text(
            text = "🎉 Lời chúc 8/3 dành cho bạn:"
        )
        when (gender) {
            Gender.FEMALE.name -> FemaleMar8()
            else -> MaleMar8()
        }
    }
}

@Composable
fun FemaleMar8() {
    val greetings = GreetingMessage.entries.map { it.message }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(greetings.size) { index ->
            Text(
                text = greetings[index],
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun MaleMar8() {
    Text(
        text = "Hôm nay là ngày của phụ nữ, hãy dành những lời chúc tốt đẹp cho họ nhé! 😊",
        fontSize = 18.sp,
        modifier = Modifier.padding(8.dp)
    )
}