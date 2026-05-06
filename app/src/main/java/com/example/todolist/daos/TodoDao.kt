package com.example.todolist.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.models.TodoDetails

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todoDetails: TodoDetails)
    @Query("SELECT * FROM TodoDetails ORDER BY id DESC")
    fun getAll(): LiveData<List<TodoDetails>>

    @Delete
    suspend fun delete(todoDetails: TodoDetails)

    @Update
    suspend fun update(todoDetails: TodoDetails)
}