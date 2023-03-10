package com.etologic.pointscorer.app.main.activity

import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.*
import com.etologic.pointscorer.app.main.fragments.main_menu.MainMenuFragment
import com.etologic.pointscorer.app.main.fragments.players.*
import javax.inject.Inject

class MainActivityNavigator @Inject constructor() {

    enum class Screens {
        MENU,
        GAME_ONE_PLAYER,
        GAME_TWO_PLAYERS,
        GAME_THREE_PLAYERS,
        GAME_FOUR_PLAYERS,
        GAME_FIVE_PLAYERS,
        GAME_SIX_PLAYERS,
        GAME_SEVEN_PLAYERS,
        GAME_EIGHT_PLAYERS,
        FINISH
    }

    fun navigateTo(activity: MainActivity, screen: Screens) {
        when (screen) {
            MENU -> goToFragment(activity, MainMenuFragment())
            GAME_ONE_PLAYER -> goToGameFragment(activity, Game1PlayerXPlayersFragment.getNewInstance())
            GAME_TWO_PLAYERS -> goToGameFragment(activity, Game2PlayersXPlayersFragment.getNewInstance())
            GAME_THREE_PLAYERS -> goToGameFragment(activity, Game3PlayersXPlayersFragment.getNewInstance())
            GAME_FOUR_PLAYERS -> goToGameFragment(activity, Game4PlayersXPlayersFragment.getNewInstance())
            GAME_FIVE_PLAYERS -> goToGameFragment(activity, Game5PlayersXPlayersFragment.getNewInstance())
            GAME_SIX_PLAYERS -> goToGameFragment(activity, Game6PlayersXPlayersFragment.getNewInstance())
            GAME_SEVEN_PLAYERS -> goToGameFragment(activity, Game7PlayersXPlayersFragment.getNewInstance())
            GAME_EIGHT_PLAYERS -> goToGameFragment(activity, Game8PlayersXPlayersFragment.getNewInstance())
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
    }

}