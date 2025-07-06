package com.example.cruddemo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cruddemo.models.Person
import com.example.cruddemo.models.PersonDao

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun personDao(): PersonDao
}