package com.etologic.pointscorer.app.common.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.etologic.pointscorer.app.common.ads.base.MyBaseInterstitialAd
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeLoadedException
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeShownException
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MyInterstitialAd private constructor(private val adUnit: String) : MyBaseInterstitialAd<InterstitialAd>(adUnit) {

    companion object {
        fun getNewInstance(adUnit: String): MyInterstitialAd = MyInterstitialAd(adUnit)
    }

    override fun load(context: Context, onAdLoaded: () -> Unit) {
        InterstitialAd.load(
            context,
            adUnit,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(logTag, adError.toString())
                    myAd = null
                    throw AdCouldNotBeLoadedException()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(logTag, "Interstitial Ad was loaded.")
                    myAd = interstitialAd
                    onAdLoaded.invoke()
                }
            }
        )
    }

    override fun show(activity: Activity, onAdShown: () -> Unit) {
        myAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    Log.d(logTag, "Interstitial Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d(logTag, "Interstitial Ad dismissed fullscreen content.")
                    myAd = null
                    onAdShown.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Called when ad fails to show.
                    Log.e(logTag, "Interstitial Ad failed to show fullscreen content.")
                    myAd = null
                    throw AdCouldNotBeShownException()
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(logTag, "Interstitial Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(logTag, "Interstitial Ad showed fullscreen content.")
                }
            }
        myAd?.show(activity)
            ?: run {
                FirebaseCrashlytics.getInstance()
                    .setCustomKey("Interstitial Ad wasn't loaded", false)
                throw AdCouldNotBeShownException()
            }
    }

}