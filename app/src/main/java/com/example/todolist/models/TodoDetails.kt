package com.example.todolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val meaning: String? = null,
    val synonyms: String? = null,
    val details: String,
    val status: ToDoStatus,
    val date: Long = System.currentTimeMillis()
)

enum class ToDoStatus{
    NEW,
    DONE
}