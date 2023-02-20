package com.etologic.pointscorer.app.main.base

import android.R.animator
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.etologic.pointscorer.app.main.fragments.main_menu.MainMenuFragment
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import com.etologic.pointscorer.app.utils.dpToPx
import com.etologic.pointscorer.databinding.*
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize.BANNER
import com.google.android.gms.ads.AdView
import com.google.firebase.crashlytics.FirebaseCrashlytics

abstract class BaseXPlayersFragment : BaseMainFragment() {

    protected lateinit var baseBinding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAds()
    }

    private fun initAds() {

        fun getAdUnitsForThisScreen(): List<String> {
            val adUnitsListForThisScreen = mutableListOf<String>()
            var numBannersFittingThisScreenWidth =
                resources.displayMetrics.widthPixels / BANNER.width.dpToPx(resources)
            if (numBannersFittingThisScreenWidth > MainMenuFragment.adUnitsList.size) {
                FirebaseCrashlytics.getInstance()
                    .setCustomKey("MoreThan6BannersCouldBeAdded", numBannersFittingThisScreenWidth)
                numBannersFittingThisScreenWidth = MainMenuFragment.adUnitsList.size
            }
            for (i in 0 until numBannersFittingThisScreenWidth) adUnitsListForThisScreen.add(
                MainMenuFragment.adUnitsList[i]
            )
            return adUnitsListForThisScreen
        }

        fun buildBannerAd(adUnit: String): AdView? =
            context?.let {
                AdView(it).apply {
                    adUnitId = adUnit
                    setAdSize(BANNER)
                }
            }

        fun addView(bannerAdView: AdView) {
            when (baseBinding) {
                is GameBOnePlayerFragmentBinding ->
                    (baseBinding as GameBOnePlayerFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
                is GameCTwoPlayersFragmentBinding ->
                    (baseBinding as GameCTwoPlayersFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
                is GameDThreePlayersFragmentBinding ->
                    (baseBinding as GameDThreePlayersFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
                is GameEFourPlayersFragmentBinding ->
                    (baseBinding as GameEFourPlayersFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
                is GameFFivePlayersFragmentBinding ->
                    (baseBinding as GameFFivePlayersFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
                is GameGSixPlayersFragmentBinding ->
                    (baseBinding as GameGSixPlayersFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
                is GameHSevenPlayersFragmentBinding ->
                    (baseBinding as GameHSevenPlayersFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
                is GameIEightPlayersFragmentBinding ->
                    (baseBinding as GameIEightPlayersFragmentBinding).llMainMenuAdsContainer.addView(
                        bannerAdView
                    )
            }
        }

        fun loadAd(adView: AdView) {
            val adRequest = AdRequest.Builder().apply {
                val extras = Bundle().apply { putString("npa", "1") }
                addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            }.build()
            adView.loadAd(adRequest)
        }

        getAdUnitsForThisScreen().forEach { adUnit ->
            buildBannerAd(adUnit)?.let { bannerAdView ->
                addView(bannerAdView)
                loadAd(bannerAdView)
            }
        }
    }

    protected fun initPlayerFragment(
        playerId: Int,
        frameLayout: Int,
        nameSize: Int,
        nameMarginTop: Int,
        pointsSize: Int
    ) {
        val bundle = Bundle()
        bundle.putInt(PlayerFragment.KEY_PLAYER_ID, playerId)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_SIZE, nameSize)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_MARGIN_TOP, nameMarginTop)
        bundle.putInt(PlayerFragment.KEY_PLAYER_POINTS_SIZE, pointsSize)
        val fragment = PlayerFragment()
        fragment.arguments = bundle

        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(animator.fade_in, animator.fade_out)
            .add(frameLayout, fragment)
            .commit()
    }
}
