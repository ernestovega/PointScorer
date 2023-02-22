package com.etologic.pointscorer.app.common.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.ads.base.MyBaseInterstitialAd
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MyRewardedAd(private val adUnit: String) : MyBaseInterstitialAd<RewardedAd>(adUnit) {

    override fun load(context: Context, onAdLoaded: () -> Unit){
        RewardedAd.load(
            context,
            adUnit,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(logTag, adError.toString())
                    myAd = null
                    throw AdCouldNotBeLoadedException()
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(logTag, "Rewarded Ad was loaded.")
                    myAd = rewardedAd
                    onAdLoaded.invoke()
                }
            }
        )
    }

    override fun show(activity: Activity, onAdShown: () -> Unit) {
        myAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    Log.d(logTag, "Rewarded Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(logTag, "Rewarded Ad dismissed fullscreen content.")
                    myAd = null
                    onAdShown.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Called when ad fails to show.
                    Log.e(logTag, "Rewarded Ad failed to show fullscreen content.")
                    myAd = null
                    throw AdCouldNotBeShownException()
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(logTag, "Rewarded Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(logTag, "Rewarded Ad showed fullscreen content.")
                }
            }
        myAd?.show(activity) { rewardItem ->
            Log.d(logTag, "User earned the reward($rewardItem)")
            myAd = null
        }
            ?: run {
                FirebaseCrashlytics.getInstance().setCustomKey("Rewarded Ad wasn't loaded", false)
                Toast.makeText(activity, activity.getString(R.string.ups), Toast.LENGTH_SHORT)
                    .show()
                throw AdCouldNotBeShownException()
            }
    }

}