package com.example.cruddemo.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val age: Int
)

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAll(): Flow<List<Person>>

    @Query("SELECT * FROM person where id = :id")
    fun getPersonById(id: Int): Person

    @Insert
    suspend fun addPerson(person: Person)

    @Query("DELETE FROM person WHERE id = :id")
    suspend fun deletePersonById(id: Int)
}