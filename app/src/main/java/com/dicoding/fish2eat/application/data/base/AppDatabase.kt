package com.dicoding.fish2eat.application.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.fish2eat.application.data.response.FavoriteDao
import com.dicoding.fish2eat.application.data.response.FavoriteRecipe

@Database(entities = [FavoriteRecipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favorite_recipes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}