package com.example.lec_db_1

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            Log.d("TaskDatabase", "getInstance called") // Лог на початку методу
            synchronized(this) {
                Log.d("TaskDatabase", "Inside synchronized block") // Лог перед перевіркою INSTANCE
                var instance = INSTANCE
                if (instance == null) {
                    Log.d("TaskDatabase", "Instance is null, creating database") // Лог перед створенням бази
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "tasks_database"
                    ).build()
                    INSTANCE = instance
                    Log.d("TaskDatabase", "Database instance created") // Лог після створення бази
                } else {
                    Log.d("TaskDatabase", "Using existing database instance") // Лог, якщо база вже існує
                }
                return instance
            }
        }
    }
}