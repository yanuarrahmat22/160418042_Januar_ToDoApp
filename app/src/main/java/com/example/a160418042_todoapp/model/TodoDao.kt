package com.example.a160418042_todoapp.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:Todo)
//    @Query("SELECT * FROM todo")
//    suspend fun selectAllTodo(): List<Todo>
    @Query("SELECT * FROM todo ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<Todo>
    @Query("SELECT * FROM todo WHERE uuid=:id")
    suspend fun selectTodo(id:Int): Todo
    @Delete
    suspend fun deleteTodo(todo:Todo)
    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid=:id")
    suspend fun update(title:String, notes:String, priority:Int, id:Int)
    @Query("UPDATE todo SET is_done=1 WHERE uuid=:id")
    suspend fun isDoneTodo(id:Int)

}