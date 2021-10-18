package me.mayankchoudhary.simpletodolist

import android.app.Application
import me.mayankchoudhary.simpletodolist.database.TodoDatabase

// need to provide a custom subclass of the Application class,
// and create a lazy property that will hold the result of getDatabase().
class TodoApplication : Application() {
    val database : TodoDatabase by lazy {TodoDatabase.getDatabase(this)}
}