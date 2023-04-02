package com.etologic.pointscorer.common.injection

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PointsScorerApplication : Application() {

    companion object {
        //ToDo: private const val TEST_ADS_DEVICE_ID_POCO_M3 = ""
    }

    override fun onCreate() {
        super.onCreate()
        initFirebase()
        initAds()
    }

    private fun initFirebase() {
        FirebaseCrashlytics.getInstance()
        FirebaseAnalytics.getInstance(this)
    }

    private fun initAds() {
        MobileAds.initialize(this)
        MobileAds.setAppMuted(true)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                //ToDo: .setTestDeviceIds(listOf(TEST_ADS_DEVICE_ID_POCO_M3))
                .build()
        )
    }
}
