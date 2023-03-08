package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.NavigationData.AppScreens.*
import com.etologic.pointscorer.app.main.dialogs.PlayerSettingsMenuDialogFragment
import com.etologic.pointscorer.app.main.dialogs.ShowAdCountDownDialogFragment
import com.etologic.pointscorer.app.main.fragments.main_menu.MainMenuFragment
import com.etologic.pointscorer.app.main.fragments.players.*
import javax.inject.Inject

class MainActivityNavigator @Inject constructor() {

    data class NavigationData(
        val screen: AppScreens,
        val data: Bundle? = null,
        val listener: Any? = null
    ) {
        enum class AppScreens {
            MENU,
            GAME_ONE_PLAYER,
            GAME_TWO_PLAYERS,
            GAME_THREE_PLAYERS,
            GAME_FOUR_PLAYERS,
            GAME_FIVE_PLAYERS,
            GAME_SIX_PLAYERS,
            GAME_SEVEN_PLAYERS,
            GAME_EIGHT_PLAYERS,
            GAME_INTERSTITIAL_COUNTDOWN,
            GAME_INTERSTITIAL,
            GAME_PLAYER_SETTINGS,
            FINISH
        }
    }

    fun navigateTo(activity: MainActivity, navigationData: NavigationData) {
        when (navigationData.screen) {
            MENU -> goToFragment(activity, MainMenuFragment())
            GAME_ONE_PLAYER -> goToGameFragment(activity, Game1PlayerFragment.getNewInstance())
            GAME_TWO_PLAYERS -> goToGameFragment(activity, Game2PlayersFragment.getNewInstance())
            GAME_THREE_PLAYERS -> goToGameFragment(activity, Game3PlayersFragment.getNewInstance())
            GAME_FOUR_PLAYERS -> goToGameFragment(activity, Game4PlayersFragment.getNewInstance())
            GAME_FIVE_PLAYERS -> goToGameFragment(activity, Game5PlayersFragment.getNewInstance())
            GAME_SIX_PLAYERS -> goToGameFragment(activity, Game6PlayersFragment.getNewInstance())
            GAME_SEVEN_PLAYERS -> goToGameFragment(activity, Game7PlayersFragment.getNewInstance())
            GAME_EIGHT_PLAYERS -> goToGameFragment(activity, Game8PlayersFragment.getNewInstance())
            GAME_INTERSTITIAL_COUNTDOWN -> showGameInterstitialCountDownDialog(activity)
            GAME_INTERSTITIAL -> showGameInterstitialAd(activity)
            GAME_PLAYER_SETTINGS -> showGamePlayerSettings(activity, navigationData)
            FINISH -> activity.finish()
        }
    }

    private fun goToFragment(activity: MainActivity, fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .replace(R.id.flMain, fragment)
            .commit()
    }

    private fun goToGameFragment(activity: MainActivity, fragment: Fragment) {
        goToFragment(activity, fragment)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (activity.viewModel.myInterstitialAdForGame == null) {
            loadNewGameInterstitialAd(activity)
        }
    }

    private fun loadNewGameInterstitialAd(activity: MainActivity) {
        with(MyInterstitialAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_INTERSTITIAL_GAME)) {
            try {
                load(activity) { activity.viewModel.myInterstitialAdForGame = this }
            } catch (_: MyBaseAd.AdCouldNotBeLoadedException) {
            }
        }
    }

    private fun showGameInterstitialCountDownDialog(activity: MainActivity) {
        ShowAdCountDownDialogFragment()
            .show(activity.supportFragmentManager, ShowAdCountDownDialogFragment.TAG)
    }

    private fun showGameInterstitialAd(activity: MainActivity) {
        activity.viewModel.myInterstitialAdForGame?.show(activity) {
            loadNewGameInterstitialAd(activity)
        }
    }

    private fun showGamePlayerSettings(activity: MainActivity, navigationData: NavigationData) {
        PlayerSettingsMenuDialogFragment.newInstance(
            navigationData.data,
            navigationData.listener as PlayerSettingsMenuDialogFragment.PlayerSettingsMenuDialogListener
        ).show(activity.supportFragmentManager, PlayerSettingsMenuDialogFragment.TAG)
    }

}