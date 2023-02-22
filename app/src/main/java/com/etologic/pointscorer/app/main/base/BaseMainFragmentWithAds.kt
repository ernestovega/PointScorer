package com.etologic.pointscorer.app.main.base

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.app.utils.AdsExtensions.load
import com.etologic.pointscorer.app.utils.dpToPx
import com.etologic.pointscorer.databinding.*
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.crashlytics.FirebaseCrashlytics

abstract class BaseMainFragmentWithAds : BaseMainFragment() {

    companion object {
        val adUnitsList = listOf(
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_1,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_2,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_3,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_4,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_5,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_6,
        )
    }

    protected lateinit var baseBinding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAds()
    }

    private fun initAds() {

        fun getAdUnitsForThisScreen(): List<String> {
            val adUnitsListForThisScreen = mutableListOf<String>()
            var numBannersFittingThisScreenWidth =
                resources.displayMetrics.widthPixels / AdSize.BANNER.width.dpToPx(resources)
            if (numBannersFittingThisScreenWidth > adUnitsList.size) {
                FirebaseCrashlytics.getInstance()
                    .setCustomKey("MoreThan6BannersCouldBeAdded", numBannersFittingThisScreenWidth)
                numBannersFittingThisScreenWidth = adUnitsList.size
            }
            for (i in 0 until numBannersFittingThisScreenWidth) adUnitsListForThisScreen.add(
                adUnitsList[i]
            )
            return adUnitsListForThisScreen
        }

        fun buildBannerAd(adUnit: String): AdView? =
            context?.let {
                AdView(it).apply {
                    adUnitId = adUnit
                    setAdSize(AdSize.BANNER)
                }
            }

        if (activityViewModel.shouldShowAds) {
            getAdUnitsForThisScreen().forEach { adUnit ->
                buildBannerAd(adUnit)?.let { bannerAdView ->
                    getAdContainer()?.apply {
                        visibility = VISIBLE
                        addView(bannerAdView)
                    }
                    bannerAdView.load()
                }
            }
        } else {
            getAdContainer()?.visibility = GONE
        }
    }

    private fun getAdContainer(): LinearLayout? =
        when (baseBinding) {
            is MainMenuFragmentBinding -> {
                (baseBinding as MainMenuFragmentBinding).llMainMenuAdsContainer
            }
            is GameBOnePlayerFragmentBinding -> {
                (baseBinding as GameBOnePlayerFragmentBinding).llMainMenuAdsContainer
            }
            is GameCTwoPlayersFragmentBinding -> {
                (baseBinding as GameCTwoPlayersFragmentBinding).llMainMenuAdsContainer
            }
            is GameDThreePlayersFragmentBinding -> {
                (baseBinding as GameDThreePlayersFragmentBinding).llMainMenuAdsContainer
            }
            is GameEFourPlayersFragmentBinding -> {
                (baseBinding as GameEFourPlayersFragmentBinding).llMainMenuAdsContainer
            }
            is GameFFivePlayersFragmentBinding -> {
                (baseBinding as GameFFivePlayersFragmentBinding).llMainMenuAdsContainer
            }
            is GameGSixPlayersFragmentBinding -> {
                (baseBinding as GameGSixPlayersFragmentBinding).llMainMenuAdsContainer
            }
            is GameHSevenPlayersFragmentBinding -> {
                (baseBinding as GameHSevenPlayersFragmentBinding).llMainMenuAdsContainer
            }
            is GameIEightPlayersFragmentBinding -> {
                (baseBinding as GameIEightPlayersFragmentBinding).llMainMenuAdsContainer
            }
            else -> null
        }

}
