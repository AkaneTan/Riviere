package org.akanework.riviere

import android.app.Application
import com.google.android.material.color.DynamicColors

class Riviere : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}