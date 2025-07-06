package com.example.cruddemo.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val age: Int
)

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAll(): Flow<List<Person>>

    @Insert
    suspend fun addPerson(person: Person)
}

object Hardcoded {
    val hardcodedList: MutableList<Person> = mutableListOf(
        Person(
            id = 1,
            name= "Anish",
            age= 26
        ),
        Person(
            id = 2,
            name= "Raj",
            age= 33
        ),
        Person(
            id = 3,
            name= "Shyam",
            age= 21
        ),
    )

    fun add(person: Person) {
        this.hardcodedList.add(person)
    }
}