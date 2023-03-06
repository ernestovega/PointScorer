package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.*
import com.etologic.pointscorer.app.main.dialogs.ShowAdCountDownDialogFragment
import com.etologic.pointscorer.app.main.fragments.main_menu.MainMenuFragment
import com.etologic.pointscorer.app.main.fragments.players.*
import com.etologic.pointscorer.databinding.MainActivityBinding
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
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

            fun goToFragment(fragment: Fragment) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.flMain, fragment)
                    .commit()
            }

            fun goToGameFragment(fragment: Fragment) {
                goToFragment(fragment)
                window.addFlags(FLAG_KEEP_SCREEN_ON)
                if (viewModel.myInterstitialAdForGame == null ) {
                    loadNewGameInterstitialAd()
                }
            }

            fun showGameInterstitialCountDownDialog() {
                ShowAdCountDownDialogFragment()
                    .show(supportFragmentManager, ShowAdCountDownDialogFragment.TAG)
            }

            fun showGameInterstitialAd() {
                viewModel.myInterstitialAdForGame?.show(this) {
                    loadNewGameInterstitialAd()
                }
            }

            when (screen) {
                MENU -> goToFragment(MainMenuFragment())
                GAME_ONE_PLAYER -> goToGameFragment(Game1PlayerFragment())
                GAME_TWO_PLAYERS -> goToGameFragment(Game2PlayersFragment())
                GAME_THREE_PLAYERS -> goToGameFragment(Game3PlayersFragment())
                GAME_FOUR_PLAYERS -> goToGameFragment(Game4PlayersFragment())
                GAME_FIVE_PLAYERS -> goToGameFragment(Game5PlayersFragment())
                GAME_SIX_PLAYERS -> goToGameFragment(Game6PlayersFragment())
                GAME_SEVEN_PLAYERS -> goToGameFragment(Game7PlayersFragment())
                GAME_EIGHT_PLAYERS -> goToGameFragment(Game8PlayersFragment())
                GAME_INTERSTITIAL_COUNTDOWN -> showGameInterstitialCountDownDialog()
                GAME_INTERSTITIAL -> showGameInterstitialAd()
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

    private fun loadNewGameInterstitialAd() {
        with(MyInterstitialAd(BuildConfig.ADMOB_ADUNIT_INTERSTITIAL_GAME)) {
            try {
                load(this@MainActivity) { viewModel.myInterstitialAdForGame = this }
            } catch (_: MyBaseAd.AdCouldNotBeLoadedException) {
            }
        }
    }

    companion object {
        private const val TEST_ADS_DEVICE_ID_HUAWEI_P20 = "712C0F78415783A418DE51CD38EC03C6"
    }
}