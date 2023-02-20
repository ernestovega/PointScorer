package com.etologic.pointscorer.app.main.dialogs.finish_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.FINISH
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.app.utils.AdsExtensions.load
import com.etologic.pointscorer.databinding.FinishMenuDialogFragmentBinding
import com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE
import com.google.android.gms.ads.AdView
import javax.inject.Inject


class FinishMenuDialogFragment @Inject constructor() : BaseMainDialogFragment() {

    companion object {
        const val TAG = "FinishMenuDialogFragment"
    }

    private var fragmentBinding: FinishMenuDialogFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    private var bannerAdView: AdView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FinishMenuDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fun initListeners() {
            with(binding) {
                btFinishMenuExit.setOnClickListener {
                    activityViewModel.navigateTo(FINISH)
                    dismiss()
                }
                btFinishMenuCancel.setOnClickListener {
                    dismiss()
                }
            }
        }

        fun initAd() {
            bannerAdView = context?.let {
                AdView(it).apply {
                    adUnitId = BuildConfig.ADMOB_ADUNIT_BANNER_FINISH_MENU
                    setAdSize(MEDIUM_RECTANGLE)
                }
            }
            bannerAdView?.let { binding.flFinishMenuMediumRectangleAdContainer.addView(it) }
        }

        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initAd()
    }

    override fun onStart() {
        super.onStart()
        bannerAdView?.load()
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}
