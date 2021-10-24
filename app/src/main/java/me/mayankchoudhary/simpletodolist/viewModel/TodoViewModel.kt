package me.mayankchoudhary.simpletodolist.viewModel

import android.util.Log
import androidx.lifecycle.*
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.mayankchoudhary.simpletodolist.database.TodoDao
import me.mayankchoudhary.simpletodolist.model.Todo
import java.lang.Exception

class TodoViewModel(private val todoDao: TodoDao) : ViewModel() {
    // Cache all items form the database using LiveData.
//    private var _allTodos = getSearched("")

    private val currentQuery = MutableLiveData<String>(DEFAULT_QUERY)

    val allTodos = currentQuery.switchMap {
        if(it == "") {
            todoDao.getAllTodo().asLiveData()
        } else {
            todoDao.GetSearched(it).asLiveData()
        }
    }
//    get() = _allTodos

    init {
    }


//    fun getSearched(query: String): LiveData<List<Todo>> {
//        if (query == "") {
//            _allTodos = todoDao.getAllTodo().asLiveData()
//        } else {
//            _allTodos = todoDao.GetSearched(query).asLiveData()
//            Log.d("My", allTodos.toString())
//        }
//
//        return _allTodos
//    }
    fun getSearched(query: String){
        currentQuery.value = query
    }


    // delete todo
    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }
    }

    // updateTodo
    fun updateTodoItem(isCompleted: Boolean, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.updateTodo(!isCompleted, id)
        }
    }

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

    companion object {
        //        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }

}