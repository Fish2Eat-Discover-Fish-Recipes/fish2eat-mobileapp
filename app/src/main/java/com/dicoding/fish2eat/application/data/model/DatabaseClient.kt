package com.dicoding.fish2eat.application.data.model

import android.content.Context
import androidx.room.Room
import com.dicoding.fish2eat.application.data.base.AppDatabase

object DatabaseClient {
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (instance == null) {
            synchronized(AppDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_db"
                ).build()
            }
        }
        return instance!!
    }
}