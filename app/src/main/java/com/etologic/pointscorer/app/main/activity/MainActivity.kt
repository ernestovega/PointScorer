package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.*
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.app.main.fragments.*
import com.etologic.pointscorer.databinding.MainActivityBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    
    @Inject
    internal lateinit var viewModelFactory: MainActivityViewModelFactory
    private lateinit var viewModel: MainActivityViewModel
    private val backCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            viewModel.navigateBack()
        }
    }
    private var _activityBinding: MainActivityBinding? = null
    private val binding get() = _activityBinding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initViewBinding()
        initWindowInsets()
        initViewModel()
        initObservers()
        initBackNavigation()
    }
    
    private fun initViewBinding() {
        _activityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    /* Since targetSdk 35 the app is drawn edge-to-edge, so keep the content clear of the
     * status/navigation bars, display cutouts and the keyboard. The wood window background
     * stays visible behind the transparent system bars. */
    private fun initWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout() or
                    WindowInsetsCompat.Type.ime()
            )
            view.updatePadding(left = insets.left, top = insets.top, right = insets.right, bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    /* onBackPressed() is no longer called on Android 16 for apps targeting API 36
     * (predictive back), so back handling goes through the OnBackPressedDispatcher.
     * The callback is only enabled inside a game, so on the menu the system handles back
     * and can show the predictive back-to-home animation. */
    private fun initBackNavigation() {
        onBackPressedDispatcher.addCallback(this, backCallback)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
    }
    
    private fun initObservers() {
        viewModel.liveScreen.observe(this) {
            backCallback.isEnabled = it != MENU
            when (it) {
                MENU -> goToFragment(MenuFragment())
                ONE_PLAYER -> goToFragment(Game1PlayerFragment())
                TWO_PLAYER -> goToFragment(Game2PlayersFragment())
                THREE_PLAYER -> goToFragment(Game3PlayersFragment())
                FOUR_PLAYER -> goToFragment(Game4PlayersFragment())
                FIVE_PLAYER -> goToFragment(Game5PlayersFragment())
                SIX_PLAYER -> goToFragment(Game6PlayersFragment())
                SEVEN_PLAYER -> goToFragment(Game7PlayersFragment())
                EIGHT_PLAYER -> goToFragment(Game8PlayersFragment())
                else -> finish()
            }
        }
    }
    
    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .replace(R.id.flMain, fragment)
            .commit()
        
        handleKeepScreenOn(fragment)
    }
    
    private fun handleKeepScreenOn(fragment: Fragment) {
        if (fragment is BaseXPlayersFragment)
            window.addFlags(FLAG_KEEP_SCREEN_ON)
        else
            window.clearFlags(FLAG_KEEP_SCREEN_ON)
    }
}