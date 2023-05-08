package com.example.assignment_day06.model

data class Todo(
    val title : String,
    val text : String,
    var isChecked : Boolean
):java.io.Serializable
