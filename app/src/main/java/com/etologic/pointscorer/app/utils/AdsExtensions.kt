package com.etologic.pointscorer.app.utils

import android.os.Bundle
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

object AdsExtensions {

    fun AdView.load() {
        val adRequest = AdRequest.Builder().apply {
            val extras = Bundle().apply { putString("npa", "1") }
            addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        }.build()
        loadAd(adRequest)
    }

}