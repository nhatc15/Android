package com.example.assignment_day06.database

import com.example.assignment_day06.model.Todo

object Database{
    val todolist = mutableListOf<Todo>(
        Todo("Title 1","Get up",false),
        Todo("Title 2","Do morning exercises",false),
        Todo("Title 3","Go to work",false),
    )
    fun getAllNote():MutableList<Todo> = todolist



}