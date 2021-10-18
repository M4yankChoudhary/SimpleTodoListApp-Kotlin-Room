package me.mayankchoudhary.simpletodolist.viewModel

import android.util.Log
import androidx.lifecycle.*
import androidx.room.Dao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.mayankchoudhary.simpletodolist.database.TodoDao
import me.mayankchoudhary.simpletodolist.model.Todo
import java.lang.Exception

class TodoViewModel(private val todoDao: TodoDao) : ViewModel() {
    // Cache all items form the database using LiveData.
    val allTodos: LiveData<List<Todo>> = todoDao.getAllTodo().asLiveData()

    // *************  add or insert
    fun addNewTodo(name: String) {
        val newItem = getNewTodoEntry(name)
        insertTodo(newItem)
    }

    /**
     * Launching a new coroutine to insert an task in a non-blocking way
     */
    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insert(todo)
        }
    }

    /**
     * Returns an instance of the [Todo] entity class
     * with the todo name entered by the user.
     * This will be used to add a new entry to the Todo database.
     */
    private fun getNewTodoEntry(name: String): Todo {
        return Todo(
            name = name,
        )
    }

}