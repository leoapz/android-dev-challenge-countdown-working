/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.model.TimerViewModel
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    CountdownScreen()
}

@Composable
fun CountdownScreen() {
    val countdownViewModel: TimerViewModel = viewModel()
    val inputTime: Int by countdownViewModel.inputTime.observeAsState(3)
    val timerIsRunning: Boolean by countdownViewModel.timerIsRunning.observeAsState(false)
    val showRocket: Boolean by countdownViewModel.timerIsFinished.observeAsState(false)

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Temporizador") }
                )
            },
            content = {
                CountdownContent(
                    inputTime = inputTime,
                    onInputTimeChange = { countdownViewModel.onInputTimeChange(it.toInt()) },
                    onClickButton = { countdownViewModel.startTimer() },
                    timerIsRunning = timerIsRunning,
                    timerIsFinished = showRocket
                )
            }
        )
    }
}

@Composable
fun CountdownContent(
    inputTime: Int,
    onInputTimeChange: (String) -> Unit,
    onClickButton: () -> Unit,
    timerIsRunning: Boolean,
    timerIsFinished: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!timerIsRunning) {
            OutlinedTextField(
                value = inputTime.toString(),
                onValueChange = { onInputTimeChange(it) },
                label = { Text("Ingresa un numero") },
                modifier = Modifier.padding(20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        if (timerIsFinished) {
            Text(
                text = "\uD83C\uDFC1",
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )
        }

        CountdownText(value = inputTime, timerIsFinished = timerIsFinished)

        Spacer(modifier = Modifier.height(20.dp))

        if (!timerIsRunning) {
            Button(
                onClick = { onClickButton() }
            ) {
                Text(text = "Comenzar")
            }
        }
    }
}

@Composable
fun CountdownText(value: Int, timerIsFinished: Boolean) {
    val textToShow = if (!timerIsFinished) "$value" else "Fin!"

    Text(
        text = textToShow,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center
    )
}

@Composable
@Preview(showSystemUi = true)
fun CountdownContentPreview() {
    CountdownContent(
        inputTime = 20,
        onInputTimeChange = {  },
        onClickButton = { },
        timerIsRunning = false,
        timerIsFinished = true
    )
}
