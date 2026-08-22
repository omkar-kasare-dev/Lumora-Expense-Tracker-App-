package com.finance.lumora

/*
import android.app.Application
import com.finance.lumora.notifications.NotificationChannels
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class LumoraApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        /**
         * Create Lumora notification channels.
         */
        NotificationChannels.createChannels(this)
    }
}

 */



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

    /**
     * Provides Hilt's WorkerFactory to WorkManager.
     *
     * This allows @HiltWorker classes such as
     * BudgetAlertWorker to receive injected dependencies.
     */
    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
}





