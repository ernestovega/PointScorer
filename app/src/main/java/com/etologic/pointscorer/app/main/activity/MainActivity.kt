package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.*
import com.etologic.pointscorer.app.main.fragments.main_menu.MainMenuFragment
import com.etologic.pointscorer.app.main.fragments.players.*
import com.etologic.pointscorer.databinding.MainActivityBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firebaseCrashlytics: FirebaseCrashlytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        setOnBackPressedCallback()
        initFirebase()
        initAds()
    }

    private fun initViewBinding() {
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]

        viewModel.screenObservable.observe(this) { screen ->

            fun goToFragment(fragment: Fragment, keepScreenOn: Boolean = false) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.flMain, fragment)
                    .commit()

                if (keepScreenOn) {
                    window.addFlags(FLAG_KEEP_SCREEN_ON)
                } else {
                    window.clearFlags(FLAG_KEEP_SCREEN_ON)
                }
            }

            when (screen) {
                MENU -> goToFragment(MainMenuFragment())
                ONE_PLAYER -> goToFragment(Game1PlayerFragment(), true)
                TWO_PLAYER -> goToFragment(Game2PlayersFragment(), true)
                THREE_PLAYER -> goToFragment(Game3PlayersFragment(), true)
                FOUR_PLAYER -> goToFragment(Game4PlayersFragment(), true)
                FIVE_PLAYER -> goToFragment(Game5PlayersFragment(), true)
                SIX_PLAYER -> goToFragment(Game6PlayersFragment(), true)
                SEVEN_PLAYER -> goToFragment(Game7PlayersFragment(), true)
                EIGHT_PLAYER -> goToFragment(Game8PlayersFragment(), true)
                else -> finish()
            }
        }
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.screenObservable.value == MENU) {
                    viewModel.navigateTo(FINISH)
                } else {
                    viewModel.navigateTo(MENU)
                }
            }
        })
    }


    private fun initFirebase() {
        firebaseCrashlytics = FirebaseCrashlytics.getInstance()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }

    private fun initAds() {
        MobileAds.initialize(this)
        MobileAds.setAppMuted(true)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(TEST_ADS_DEVICE_ID_HUAWEI_P20))
                .build()
        )
    }

    companion object {
        private const val TEST_ADS_DEVICE_ID_HUAWEI_P20 = "712C0F78415783A418DE51CD38EC03C6"
    }
}