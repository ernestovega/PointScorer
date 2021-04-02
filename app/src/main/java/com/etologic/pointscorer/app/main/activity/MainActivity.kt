package com.etologic.pointscorer.app.main.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.MainScreens
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.MainScreens.*
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
        viewModel.getScreen().observe(this, Observer(this::screenObserver))
    }
    
    private fun screenObserver(screen: MainScreens) {
            when (screen) {
                MENU -> goToFragment(AMenuFragment())
                ONE_PLAYER -> goToFragment(BOnePlayerFragment())
                TWO_PLAYER -> goToFragment(CTwoPlayersFragment())
                THREE_PLAYER -> goToFragment(DThreePlayersFragment())
                FOUR_PLAYER -> goToFragment(EFourPlayersFragment())
                FIVE_PLAYER -> goToFragment(FFivePlayersFragment())
                SIX_PLAYER -> goToFragment(GSixPlayersFragment())
                SEVEN_PLAYER -> goToFragment(HSevenPlayersFragment())
                EIGHT_PLAYER -> goToFragment(IEightPlayersFragment())
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