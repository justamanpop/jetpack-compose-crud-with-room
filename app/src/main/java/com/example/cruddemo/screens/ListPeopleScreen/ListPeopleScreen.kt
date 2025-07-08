package com.example.cruddemo.screens.ListPeopleScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cruddemo.models.Hardcoded
import com.example.cruddemo.models.Person
import com.example.cruddemo.screens.AddPersonScreenRoute
import com.example.cruddemo.screens.Routes
import com.example.cruddemo.ui.theme.CrudDemoTheme
import kotlinx.coroutines.launch

@Composable
fun ListPeopleScreen(viewModel: ListPeopleScreenViewModel, navController: NavController) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val people = state.people.collectAsState(listOf()).value

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate<Routes>(AddPersonScreenRoute)
        }) {
            Icon(Icons.Default.Add, contentDescription = "Add button")
        }
    }) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 10.dp))
            }
            items(people.size, { idx -> people[idx].id!! }) { idx ->
                PersonCard(people[idx], viewModel::deletePerson, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun PersonCard(person: Person, deleteFunction: suspend (Int) -> Unit, modifier: Modifier) {
    val coroutineScope = rememberCoroutineScope()
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), modifier = modifier) {
        Row(modifier = modifier) {
            Column(modifier = Modifier.padding(start = 8.dp).weight(1f)) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Text("Name: ${person.name}")
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    Text("Age: ${person.age}")
                }
            }
            IconButton(onClick = {
               coroutineScope.launch {
                    deleteFunction(person.id!!)
               }
            }) {
                Icon(Icons.Default.Delete, contentDescription = "delete person", modifier = Modifier.align(Alignment.CenterVertically))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}