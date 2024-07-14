package com.example.itemnote

import android.app.Application
import com.google.firebase.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ItemNoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // Plant a debug tree in debug builds
            Timber.plant(Timber.DebugTree())
        } else {
            // Plant a production tree in release builds

        }
    }
}