package com.etologic.pointscorer.app.common.ads.base

import android.content.Context
import android.widget.LinearLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

open class MyBaseBannerAd(private val adUnit: String, context: Context, adSize: AdSize) :
    MyBaseAd<AdView>(adUnit) {

    init {
        myAd = AdView(context).apply {
            adUnitId = adUnit
            setAdSize(adSize)
        }
    }

    override fun load(context: Context, onAdLoaded: () -> Unit) {
        myAd?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                onAdLoaded.invoke()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                throw AdCouldNotBeLoadedException()
            }
        }
        myAd?.loadAd(adRequest) ?: throw AdCouldNotBeLoadedException()
    }

    @Throws(AdCouldNotBeShownException::class)
    fun show(adContainer: LinearLayout?) {
        adContainer?.addView(myAd) ?: throw AdCouldNotBeShownException()
    }

}