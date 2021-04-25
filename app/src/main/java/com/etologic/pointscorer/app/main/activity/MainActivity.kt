package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.*
import com.etologic.pointscorer.app.main.fragments.*
import com.etologic.pointscorer.databinding.MainActivityBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    
    @Inject
    internal lateinit var viewModelFactory: MainActivityViewModelFactory
    private lateinit var viewModel: MainActivityViewModel
    private var _activityBinding: MainActivityBinding? = null
    private val binding get() = _activityBinding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        viewModel.navigateTo(MENU)
    }
    
    private fun initViewBinding() {
        _activityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.liveScreen().observe(this, Observer(this::screenObserver))
    }
    
    private fun screenObserver(screen: MainScreens) {
        when (screen) {
            MENU -> goToFragment(MenuFragment())
            ONE_PLAYER -> goToFragment(Game1PlayerFragment())
            TWO_PLAYER -> goToFragment(Game2PlayersFragment())
            THREE_PLAYER -> goToFragment(Game3PlayersFragment())
            FOUR_PLAYER -> goToFragment(Game4PlayersFragment())
            FIVE_PLAYER -> goToFragment(Game5PlayersFragment())
            SIX_PLAYER -> goToFragment(Game6PlayersFragment())
            SEVEN_PLAYER -> goToFragment(Game7PlayersFragment())
            EIGHT_PLAYER -> goToFragment(Game8PlayersFragment())
            FINISH -> finish()
        }
    }
    
    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .replace(R.id.flMain, fragment)
            .commit()
    }
    
    override fun onBackPressed() {
        viewModel.navigateBack()
    }
}