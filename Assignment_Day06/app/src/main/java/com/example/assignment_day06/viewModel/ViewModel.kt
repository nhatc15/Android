package com.example.assignment_day06.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment_day06.database.Database
import com.example.assignment_day06.model.Todo

class ViewModel:ViewModel() {
    private val _note = MutableLiveData(Database.getAllNote())
    val note : LiveData<MutableList<Todo>>
        get() =_note

    fun getAllNote(): List<Todo>{
        return Database.todolist
    }

    fun addNote(note: Todo){
        Database.todolist.add(note)
    }

    fun updateNote(title:String, text:String, noteFound: Todo){
        Database.todolist[Database.todolist.indexOf(noteFound)] = Todo(title,text,noteFound.isChecked)
    }

    fun getNote(title: String):Todo?{
        return Database.todolist.firstOrNull() { it.title == title }
    }

    fun removeNote(note: Todo){
        Database.todolist.remove(note)
    }
}