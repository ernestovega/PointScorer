package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.NavigationData
import com.etologic.pointscorer.databinding.MainActivityBinding
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    companion object {
        private const val TEST_ADS_DEVICE_ID_HUAWEI_P20 = "712C0F78415783A418DE51CD38EC03C6"
    }

    val viewModel: MainActivityViewModel by viewModels()
    @Inject lateinit var navigator: MainActivityNavigator
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
        viewModel.screenObservable.observe(this) { navigator.navigateTo(this, it) }
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.screenObservable.value?.screen == NavigationData.AppScreens.MENU) {
                    viewModel.navigateTo(NavigationData.AppScreens.FINISH)
                } else {
                    viewModel.navigateTo(NavigationData.AppScreens.MENU)
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

    override fun onPause() {
        viewModel.cancelAdsCountDowns()
        super.onPause()
    }

}