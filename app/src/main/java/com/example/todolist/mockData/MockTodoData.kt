package com.example.todolist.mockDatas

import com.example.todolist.models.ToDoStatus
import com.example.todolist.models.TodoDetails

open class MockTodoData {

    open fun populateData(): List<TodoDetails> = listOf(

        TodoDetails(id = 2, title = "Task 2", details = "Details for task 2", status = ToDoStatus.NEW),
        TodoDetails(id = 3, title = "Task 3", details = "Details for task 3", status = ToDoStatus.NEW),
        TodoDetails(id = 4, title = "Task 4", details = "Details for task 4", status = ToDoStatus.NEW),
        TodoDetails(id = 5, title = "Task 5", details = "Details for task 5", status = ToDoStatus.NEW),
        TodoDetails(id = 6, title = "Task 6", details = "Details for task 6", status = ToDoStatus.DONE),
        TodoDetails(id = 7, title = "Task 7", details = "Details for task 7", status = ToDoStatus.NEW),
        TodoDetails(id = 8, title = "Task 8", details = "Details for task 8", status = ToDoStatus.DONE),
        TodoDetails(id = 9, title = "Task 9", details = "Details for task 9", status = ToDoStatus.NEW),
        TodoDetails(id = 10, title = "Task 10", details = "Details for task 10", status = ToDoStatus.NEW),
    )
}