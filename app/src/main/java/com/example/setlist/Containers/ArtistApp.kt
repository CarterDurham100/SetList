package com.example.setlist.Containers

import android.app.Application

class ArtistApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}