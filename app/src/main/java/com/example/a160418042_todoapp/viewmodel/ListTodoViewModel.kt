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

class ListTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
//            val db = Room.databaseBuilder(
//                getApplication(),
//                TodoDatabase::class.java, "newtododb").build()
            val db = buildDb(getApplication())
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun clearTask(todo: Todo) {
        launch {
//            val db = Room.databaseBuilder(
//                getApplication(),
//                TodoDatabase::class.java, "newtododb").build()
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun updateCek(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().isDoneTodo(todo.uuid)
        }
    }

}