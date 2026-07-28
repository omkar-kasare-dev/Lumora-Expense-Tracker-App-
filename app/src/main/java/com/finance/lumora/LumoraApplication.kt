package com.finance.lumora



import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class LumoraApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}