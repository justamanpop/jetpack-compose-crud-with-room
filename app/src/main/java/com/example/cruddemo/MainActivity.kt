package com.example.cruddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.example.cruddemo.screens.AddPersonScreen.AddPersonScreen
import com.example.cruddemo.screens.AddPersonScreen.AddPersonScreenViewModel
import com.example.cruddemo.screens.AddPersonScreenRoute
import com.example.cruddemo.screens.ListPeopleScreen.ListPeopleScreen
import com.example.cruddemo.screens.ListPeopleScreen.ListPeopleScreenViewModel
import com.example.cruddemo.screens.ListPeopleScreenRoute
import com.example.cruddemo.screens.UpdatePersonScreen.UpdatePersonScreen
import com.example.cruddemo.screens.UpdatePersonScreen.UpdatePersonScreenViewModel
import com.example.cruddemo.screens.UpdatePersonScreenRoute
import com.example.cruddemo.ui.theme.CrudDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrudDemoTheme {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "person_db"
                ).build()

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ListPeopleScreenRoute) {
                    composable<ListPeopleScreenRoute> {
                        val listPeopleScreenViewModel = ListPeopleScreenViewModel(db.personDao())
                        ListPeopleScreen(listPeopleScreenViewModel, navController)
                    }
                    composable<AddPersonScreenRoute> {
                        val addPersonScreenViewModel = AddPersonScreenViewModel(navController, db.personDao())
                        AddPersonScreen(addPersonScreenViewModel)
                    }

                    composable<UpdatePersonScreenRoute> {
                        backStackEntry ->
                        val updatePersonRoute: UpdatePersonScreenRoute = backStackEntry.toRoute()
                        val updatePersonScreenViewModel = UpdatePersonScreenViewModel(navController, db.personDao(), updatePersonRoute.personId)
                        UpdatePersonScreen(updatePersonScreenViewModel)
                    }
                }
            }
        }
    }
}
