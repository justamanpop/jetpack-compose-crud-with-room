package com.example.cruddemo.screens.ListPeopleScreen

import androidx.lifecycle.ViewModel
import com.example.cruddemo.models.Person
import com.example.cruddemo.models.PersonDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ListPeopleScreenState(
    val people: Flow<List<Person>>
)
class ListPeopleScreenViewModel(private val personDao: PersonDao): ViewModel() {
    private val _uiState = MutableStateFlow(ListPeopleScreenState(
        people = personDao.getAll()
    ))
    val uiState = _uiState.asStateFlow()

    suspend fun deletePerson(id: Int) {
        personDao.deletePersonById(id)
    }
}