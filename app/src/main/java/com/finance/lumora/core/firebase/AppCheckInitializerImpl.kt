package com.finance.lumora.core.firebase


import android.content.Context
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

class AppCheckInitializerImpl : AppCheckInitializer {

    override fun initialize(context: Context) {
        FirebaseAppCheck.getInstance()
            .installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
    }

}