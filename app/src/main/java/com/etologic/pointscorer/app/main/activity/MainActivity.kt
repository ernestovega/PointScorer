package com.etologic.pointscorer.app.main.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.FINISH
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.MENU
import com.etologic.pointscorer.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var navigator: MainActivityNavigator
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        setupOnBackPressed()
    }

    private fun initViewBinding() {
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViewModel() {
        viewModel.navigationDataObservable.observe(this) { navigator.navigateTo(this, it) }
        viewModel.gameInterstitialAdObservable.observe(this) { gameInterstitialAdObserver(it) }
    }

    private fun gameInterstitialAdObserver(myInterstitialAd: MyInterstitialAd?) {
        viewModel.showGameInterstitialAd(null)
        myInterstitialAd?.show(this)
    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.navigationDataObservable.value?.screen == MENU) {
                    viewModel.navigateTo(FINISH)
                } else {
                    viewModel.navigateTo(MENU)
                }
            }
        })
    }

}