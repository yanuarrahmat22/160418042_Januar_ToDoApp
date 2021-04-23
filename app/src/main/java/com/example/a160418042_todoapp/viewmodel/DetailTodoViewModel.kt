package com.example.a160418042_todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.a160418042_todoapp.model.Todo
import com.example.a160418042_todoapp.model.TodoDatabase
import com.example.a160418042_todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val todoLD = MutableLiveData<Todo>()
    private val job = Job()

    fun addTodo(list:List<Todo>) {
        launch {
//            val db = Room.databaseBuilder(
//                getApplication(), TodoDatabase::class.java,
//                "newtododb").build()
            val db = buildDb(getApplication())
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }

    fun fetch(uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            todoLD.value =  db.todoDao().selectTodo(uuid)
        }
    }

    fun update(title:String, notes:String, priority:Int, uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(title, notes, priority, uuid)
        }
    }


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}