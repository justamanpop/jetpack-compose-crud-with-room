package com.example.cruddemo.screens

import kotlinx.serialization.Serializable

sealed class Routes {}

@Serializable
data object ListPeopleScreenRoute: Routes()

@Serializable
data object AddPersonScreenRoute: Routes()
