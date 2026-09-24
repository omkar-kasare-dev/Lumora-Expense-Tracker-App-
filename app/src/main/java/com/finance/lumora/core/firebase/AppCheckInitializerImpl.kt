package com.finance.lumora.core.firebase

import android.content.Context
import com.finance.lumora.core.firebase.AppCheckInitializer
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class AppCheckInitializerImpl : AppCheckInitializer {

    override fun initialize(context: Context) {
        FirebaseAppCheck.getInstance()
            .installAppCheckProviderFactory(
               DebugAppCheckProviderFactory.getInstance()

                        //PlayIntegrityAppCheckProviderFactory.getInstance()
            )
    }

}