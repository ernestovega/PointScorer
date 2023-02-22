package com.etologic.pointscorer.app.common.ads.base

import android.content.Context
import android.os.Bundle
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest

abstract class MyBaseAd<T>(adUnit: String) {

    protected val logTag = "${this::class.java.simpleName}_$adUnit"
    protected var myAd: T? = null
    protected val adRequest: AdRequest = AdRequest.Builder().apply {
        addNetworkExtrasBundle(
            AdMobAdapter::class.java,
            Bundle().apply { putString("npa", "1") }
        )
    }.build()

    @Throws(AdCouldNotBeLoadedException::class)
    abstract fun load(context: Context, onAdLoaded: () -> Unit)

    class AdCouldNotBeLoadedException : Throwable()
    class AdCouldNotBeShownException : Throwable()

}
