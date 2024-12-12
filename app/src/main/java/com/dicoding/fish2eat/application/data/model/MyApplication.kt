package com.dicoding.fish2eat.application.data.model

import android.app.Application
import androidx.room.Room
import com.dicoding.fish2eat.application.data.base.AppDatabase

class App : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}