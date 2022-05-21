package com.etologic.pointscorer.app.main.dialogs.restore_all_points_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.databinding.RestoreAllPointsDialogFragmentBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class RestoreAllPointsDialogFragment : BaseMainDialogFragment() {

    companion object {
        const val TAG = "RestoreAllPointsDialogFragment"
    }

    private var fragmentBinding: RestoreAllPointsDialogFragmentBinding? = null
    private val binding get() = fragmentBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = RestoreAllPointsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAd()
        initListeners()
        initMessage()
    }

    private fun initMessage() {
        binding.tvRestoreAllPointsMessage.text = getString(R.string.this_will_restore_all_points_to_, activityViewModel.initialPointsObservable.value)
    }

    private fun initAd() {

        fun buildMediumRectangleAd(): AdView? =
            context?.let {
                AdView(it).apply {
                    adUnitId = BuildConfig.ADMOB_ADUNIT_BANNER_RESTORE_ALL_POINTS_MENU
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
            binding.flRestoreAllPointsMediumRectangleAdContainer.addView(bannerAdView)
            loadAd(bannerAdView)
        }
    }

    private fun initListeners() {
        with(binding) {
            btRestoreAllPointsOk.setOnClickListener {
                activityViewModel.restoreAllGamesPoints()
                Toast.makeText(requireContext(), R.string.all_players_points_were_restored, Toast.LENGTH_LONG).show()
                dismiss()
            }
            btRestoreAllPointsCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}
