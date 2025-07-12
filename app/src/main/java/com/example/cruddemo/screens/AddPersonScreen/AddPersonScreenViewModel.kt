package com.example.cruddemo.screens.AddPersonScreen

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cruddemo.models.Person
import com.example.cruddemo.models.PersonDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddPersonScreenState(
    val name: String = "",
    val age: String = "",
)

class AddPersonScreenViewModel(private val navController: NavController, private val personDao: PersonDao) : ViewModel() {
    private val _uiState = MutableStateFlow(AddPersonScreenState())
    val uiState = _uiState.asStateFlow()

    fun updateName(newName: String) {
        _uiState.update { currState ->
            AddPersonScreenState(
                name = newName,
                age = currState.age
            )
        }
    }

    fun updateAge(newAge: String) {
        _uiState.update { currState ->
            AddPersonScreenState(
                name = currState.name,
                age = newAge
            )
        }
    }

    suspend fun submitPerson() {
        if (_uiState.value.name == "" || _uiState.value.age == "") {
           return
        }
        personDao.addPerson(Person(
            name = _uiState.value.name,
            age = _uiState.value.age.toInt(),
        ))
        navController.popBackStack()
    }
}