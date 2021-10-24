package me.mayankchoudhary.simpletodolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.mayankchoudhary.simpletodolist.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodo(): Flow<List<Todo>>


    @Query("SELECT * FROM todo WHERE  name LIKE '%' || :name || '%'")
    fun GetSearched(name: String): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo)

    @Query("DELETE FROM todo WHERE id=:id")
    fun deleteTodo(id : Int)

    @Query("UPDATE todo SET isCompleted=:isCompleted WHERE id=:id")
    fun updateTodo(isCompleted: Boolean, id: Int)
}