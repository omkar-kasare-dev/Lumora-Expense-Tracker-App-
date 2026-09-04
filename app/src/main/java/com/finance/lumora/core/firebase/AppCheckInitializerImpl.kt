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

/*
    override fun initialize(context: Context) {
    // Production App Check provider will be configured here
    // before the release build is published.
  }

 */
}