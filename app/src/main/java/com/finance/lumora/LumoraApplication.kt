package com.finance.lumora

import android.app.Application

import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import com.finance.lumora.core.security.biometric.BiometricLifecycleObserver

import com.finance.lumora.notifications.NotificationChannels
import com.finance.lumora.domain.repository.SettingsRepository
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

import com.finance.lumora.core.firebase.AppCheckInitializerImpl
import com.finance.lumora.notifications.BudgetAlertWorkScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltAndroidApp
class LumoraApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var biometricLifecycleObserver: BiometricLifecycleObserver

    @Inject
    lateinit var budgetAlertWorkScheduler: BudgetAlertWorkScheduler

    @Inject
    lateinit var settingsRepository: SettingsRepository

    private val applicationScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)



    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(biometricLifecycleObserver)
        FirebaseApp.initializeApp(this)

        AppCheckInitializerImpl()
            .initialize(this)

        NotificationChannels.createChannels(this)

        applicationScope.launch {
            if (settingsRepository.isBudgetAlertsEnabled.first()) {
                budgetAlertWorkScheduler.scheduleBudgetAlertChecks()
            }
        }


    }

    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
}
