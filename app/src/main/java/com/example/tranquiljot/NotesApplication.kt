package com.example.tranquiljot

import android.app.Application
import com.example.tranquiljot.data.AppContainer
import com.example.tranquiljot.data.DefaultAppContainer

class NotesApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}