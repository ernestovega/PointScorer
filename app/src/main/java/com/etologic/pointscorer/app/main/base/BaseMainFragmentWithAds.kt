package com.etologic.pointscorer.app.main.base

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.app.common.ads.MyBannerAd
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeLoadedException
import com.etologic.pointscorer.app.common.utils.dpToPx
import com.etologic.pointscorer.databinding.*
import com.google.android.gms.ads.AdSize
import com.google.firebase.crashlytics.FirebaseCrashlytics

abstract class BaseMainFragmentWithAds : BaseMainFragment() {

    companion object {
        val adUnitsList = listOf(
            BuildConfig.ADMOB_ADUNIT_BANNER_1,
            BuildConfig.ADMOB_ADUNIT_BANNER_2,
            BuildConfig.ADMOB_ADUNIT_BANNER_3,
            BuildConfig.ADMOB_ADUNIT_BANNER_4,
            BuildConfig.ADMOB_ADUNIT_BANNER_5,
            BuildConfig.ADMOB_ADUNIT_BANNER_6,
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

        fun loadAndShowBannerAds() {
            val adContainer = getAdContainer()
            if (activityViewModel.shouldShowBannerAds) {
                getAdUnitsForThisScreen().forEach { adUnit ->
                    with(MyBannerAd.getNewInstance(adUnit, requireContext())) {
                        try {
                            load(requireContext()) {
                                adContainer?.apply {
                                    visibility = VISIBLE
                                    this@with.show(this@apply)
                                }
                            }
                        } catch (e: AdCouldNotBeLoadedException) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                        }
                    }
                }
            } else {
                adContainer?.visibility = GONE
            }
        }

        loadAndShowBannerAds()
    }

    private fun getAdContainer(): LinearLayout? =
        when (baseBinding) {
            is GameBOnePlayerFragmentBinding -> {
                (baseBinding as GameBOnePlayerFragmentBinding).llAdsContainer
            }
            is GameCTwoPlayersFragmentBinding -> {
                (baseBinding as GameCTwoPlayersFragmentBinding).llAdsContainer
            }
            is GameDThreePlayersFragmentBinding -> {
                (baseBinding as GameDThreePlayersFragmentBinding).llAdsContainer
            }
            is GameEFourPlayersFragmentBinding -> {
                (baseBinding as GameEFourPlayersFragmentBinding).llAdsContainer
            }
            is GameFFivePlayersFragmentBinding -> {
                (baseBinding as GameFFivePlayersFragmentBinding).llAdsContainer
            }
            is GameGSixPlayersFragmentBinding -> {
                (baseBinding as GameGSixPlayersFragmentBinding).llAdsContainer
            }
            is GameHSevenPlayersFragmentBinding -> {
                (baseBinding as GameHSevenPlayersFragmentBinding).llAdsContainer
            }
            is GameIEightPlayersFragmentBinding -> {
                (baseBinding as GameIEightPlayersFragmentBinding).llAdsContainer
            }
            else -> null
        }

}
