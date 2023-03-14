package com.etologic.pointscorer.app.main.activity

import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.canhub.cropper.CropImageContractOptions
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.*
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
        CHANGE_BACKGROUND,
        FINISH,
    }

    data class NavigationData(
        val screen: Screens,
        val activityResultLauncher: ActivityResultLauncher<*>? = null,
        val activityResultToLaunchInput: Nothing? = null,
        val activityResultToLaunchOptions: Any? = null,
    )

    fun navigateTo(activity: MainActivity, navigationData: NavigationData) {
        when (navigationData.screen) {
            MENU -> goToFragment(activity, R.id.mainMenuFragment)
            GAME_ONE_PLAYER -> goToGameFragment(activity, R.id.game1PlayerXPlayersFragment)
            GAME_TWO_PLAYERS -> goToGameFragment(activity, R.id.game2PlayersXPlayersFragment)
            GAME_THREE_PLAYERS -> goToGameFragment(activity, R.id.game3PlayersXPlayersFragment)
            GAME_FOUR_PLAYERS -> goToGameFragment(activity, R.id.game4PlayersXPlayersFragment)
            GAME_FIVE_PLAYERS -> goToGameFragment(activity, R.id.game5PlayersXPlayersFragment)
            GAME_SIX_PLAYERS -> goToGameFragment(activity, R.id.game6PlayersXPlayersFragment)
            GAME_SEVEN_PLAYERS -> goToGameFragment(activity, R.id.game7PlayersXPlayersFragment)
            GAME_EIGHT_PLAYERS -> goToGameFragment(activity, R.id.game8PlayersXPlayersFragment)
            CHANGE_BACKGROUND -> goToCropImageActivityForResult(navigationData)
            FINISH -> activity.finish()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun goToCropImageActivityForResult(navigationData: NavigationData) {
        (navigationData.activityResultLauncher as? ActivityResultLauncher<CropImageContractOptions>)?.launch(
            navigationData.activityResultToLaunchOptions as? CropImageContractOptions
        )
    }

    private fun goToFragment(activity: MainActivity, @IdRes fragmentId: Int) {
        val navOptions: NavOptions = NavOptions.Builder()
            .setEnterAnim(android.R.animator.fade_in)
            .setExitAnim(android.R.animator.fade_out)
            .build()
        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        navHostFragment.navController.navigate(fragmentId, null, navOptions)
    }

    private fun goToGameFragment(activity: MainActivity, @IdRes fragmentId: Int) {
        goToFragment(activity, fragmentId)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

}