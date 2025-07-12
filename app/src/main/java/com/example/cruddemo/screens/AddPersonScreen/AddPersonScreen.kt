package com.example.cruddemo.screens.AddPersonScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun AddPersonScreen(viewModel: AddPersonScreenViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, top = 40.dp)
        ) {
            TextField(
                value = state.name, label = { Text("Name") },
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                onValueChange = { newName ->
                    viewModel.updateName(newName)
                }, modifier = Modifier.focusRequester(focusRequester)
            )
            TextField(
                value = state.age,
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { newAge ->
                    viewModel.updateAge(newAge)
                })

            Button(
                onClick = {
                    focusManager.clearFocus()
                    coroutineScope.launch {
                        viewModel.submitPerson()
                    }
                }, colors = ButtonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            ) {
                Row {
                    Text("Add")
                    Icon(Icons.Default.Add, contentDescription = "add")
                }
            }
        }
    }
}
