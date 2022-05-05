package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.*
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.app.main.fragments.*
import com.etologic.pointscorer.databinding.MainActivityBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    
    @Inject
    internal lateinit var viewModelFactory: MainActivityViewModelFactory
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFirebase()
        initViewBinding()
        initViewModel()
        initObservers()
    }

    private fun initFirebase() {
//        FirebaseCrashlytics.getInstance().setUserId("")
            firebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
    }

    private fun initViewBinding() {
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }
    
    private fun initObservers() {
        viewModel.liveScreen.observe(this) {
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
    
    override fun onBackPressed() {
        viewModel.navigateBack()
    }
}