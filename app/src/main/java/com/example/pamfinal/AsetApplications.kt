package com.example.pamfinal

import android.app.Application
import com.example.pamfinal.dependenciesinjection.AppContainer
import com.example.pamfinal.dependenciesinjection.AsetContainer

class AsetApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= AsetContainer()
    }
}