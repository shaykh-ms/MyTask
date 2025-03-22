package com.example.mytodolist.util.analytics

import android.os.Build
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import java.util.Locale

object AnalyticsLogger {

    private var mFirebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    private const val OS_TYPE = "android"

    fun updateLog(mLogEvents: AnalyticsEvent) {
        mFirebaseAnalytics.logEvent(mLogEvents.key) {
            param("phone_model", getDeviceName())
            param("os_version", OS_TYPE)
            param("date", System.currentTimeMillis())
        }

    }

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.lowercase(Locale.getDefault())
                .startsWith(manufacturer.lowercase(Locale.getDefault()))
        ) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s.isNullOrEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            first.uppercaseChar().toString() + s.substring(1)
        }
    }
}