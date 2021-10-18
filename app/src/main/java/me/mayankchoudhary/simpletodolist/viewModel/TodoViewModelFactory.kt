package me.mayankchoudhary.simpletodolist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Dao
import androidx.room.Database
import me.mayankchoudhary.simpletodolist.database.TodoDao
import me.mayankchoudhary.simpletodolist.model.Todo
import java.lang.IllegalArgumentException

//will instantiate view model objects

class TodoViewModelFactory(private val todoDao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        Check if the modelClass is the same
//        as the InventoryViewModel class
//        and return an instance of it.
//        Otherwise, throw an exception.
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
