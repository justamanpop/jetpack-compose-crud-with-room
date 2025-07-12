package com.example.cruddemo.screens.UpdatePersonScreen

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cruddemo.models.Person
import com.example.cruddemo.models.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UpdatePersonScreenState(
    val name: String = "",
    val age: String = "",
)

class UpdatePersonScreenViewModel(
    private val navController: NavController,
    private val personDao: PersonDao,
    private val personId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdatePersonScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val person = withContext(Dispatchers.IO) {
                personDao.getPersonById(personId)
            }
            _uiState.update { _ ->
                UpdatePersonScreenState(
                    name = person.name,
                    age = person.age.toString(),
                )
            }
        }
    }

    fun updateName(newName: String) {
        _uiState.update { currState ->
            UpdatePersonScreenState(
                name = newName,
                age = currState.age
            )
        }
    }

    fun updateAge(newAge: String) {
        _uiState.update { currState ->
            UpdatePersonScreenState(
                name = currState.name,
                age = newAge
            )
        }
    }

    suspend fun submitPerson() {
        if (_uiState.value.name == "" || _uiState.value.age == "") {
            return
        }
        personDao.updatePerson(
            Person(
                id = personId,
                name = _uiState.value.name,
                age = _uiState.value.age.toInt(),
            )
        )
        navController.popBackStack()
    }
}
