package com.finance.lumora

import android.app.Application

import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import com.finance.lumora.core.security.biometric.BiometricLifecycleObserver

import com.finance.lumora.notifications.NotificationChannels
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LumoraApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var biometricLifecycleObserver: BiometricLifecycleObserver



    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(biometricLifecycleObserver)
        FirebaseApp.initializeApp(this)

        NotificationChannels.createChannels(this)


    }

    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
}





