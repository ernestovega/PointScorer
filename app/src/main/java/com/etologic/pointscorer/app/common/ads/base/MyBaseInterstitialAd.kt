package com.etologic.pointscorer.app.common.ads.base

import android.app.Activity

abstract class MyBaseInterstitialAd<T>(adUnit: String) : MyBaseAd<T>(adUnit) {

    @Throws(AdCouldNotBeShownException::class)
    abstract fun show(activity: Activity, onAdShown: () -> Unit)

}
