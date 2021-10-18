package me.mayankchoudhary.simpletodolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.mayankchoudhary.simpletodolist.model.Todo

// steps to follow
//1. Specify which entities are defined in the database.
//2. Provide access to a single instance of each DAO class.
//3. Perform any additional setup, such as pre-populating the database.

// extend room database
@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
//    When using an AppDatabase class, you want to ensure that only one
//    instance of the database exists to prevent race conditions or other
//    potential issues. The instance is stored in the companion object,
//    and you'll also need a method that either returns the existing instance,
//    or creates the database for the first time. This is defined in the companion object.
//    Add the following companion object just below the [todoDao()] function.

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}