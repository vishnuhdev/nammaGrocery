package com.example.firstcomposeapp

import android.app.Application
import android.content.Context

class ImpApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        private lateinit var instance: ImpApplication

        fun getInstance(): ImpApplication {
            return instance
        }

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}
