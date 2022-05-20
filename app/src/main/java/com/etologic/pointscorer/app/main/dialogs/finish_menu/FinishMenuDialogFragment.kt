package com.etologic.pointscorer.app.main.dialogs.finish_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.*
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.databinding.FinishMenuDialogFragmentBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class FinishMenuDialogFragment : BaseMainDialogFragment() {

    companion object {
        const val TAG = "FinishMenuDialogFragment"
    }

    private var fragmentBinding: FinishMenuDialogFragmentBinding? = null
    private val binding get() = fragmentBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FinishMenuDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAd()
        initListeners()
    }

    private fun initAd() {

        fun buildMediumRectangleAd(): AdView? =
            context?.let {
                AdView(it).apply {
                    adUnitId = BuildConfig.ADMOB_ADUNIT_BANNER_FINISH_MENU
                    adSize = AdSize.MEDIUM_RECTANGLE
                }
            }

        fun loadAd(adView: AdView) {
            val adRequest = AdRequest.Builder().apply {
                val extras = Bundle().apply { putString("npa", "1") }
                addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            }.build()
            adView.loadAd(adRequest)
        }

        buildMediumRectangleAd()?.let { bannerAdView ->
            binding.flFinishMenuMediumRectangleAdContainer.addView(bannerAdView)
            loadAd(bannerAdView)
        }
    }

    private fun initListeners() {
        with(binding) {
            btFinishMenuExit.setOnClickListener {
                activityViewModel.navigateTo(FINISH)
            }
            btFinishMenuCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}
