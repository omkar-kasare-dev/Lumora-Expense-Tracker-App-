package com.finance.lumora.core.security.biometric

import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BiometricActivityHolder @Inject constructor() {

    private var activityReference:
            WeakReference<FragmentActivity>? = null

    fun register(activity: FragmentActivity) {
        activityReference =
            WeakReference(activity)
    }

    fun unregister(activity: FragmentActivity) {

        val currentActivity =
            activityReference?.get()

        if (currentActivity === activity) {
            activityReference?.clear()
            activityReference = null
        }
    }

    fun getActivity(): FragmentActivity? {
        return activityReference?.get()
    }
}