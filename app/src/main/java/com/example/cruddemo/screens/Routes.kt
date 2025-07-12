package com.example.cruddemo.screens

import com.example.cruddemo.models.Person
import kotlinx.serialization.Serializable

sealed class Routes {}

@Serializable
data object ListPeopleScreenRoute: Routes()

@Serializable
data object AddPersonScreenRoute: Routes()

@Serializable
data class UpdatePersonScreenRoute(val personId: Int): Routes()
