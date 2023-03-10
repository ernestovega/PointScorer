package com.etologic.pointscorer.app.main.fragments.players

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.common.Constants.A_MINUTE_IN_MILLIS
import com.etologic.pointscorer.databinding.AdWillBeShownDialogBinding
import javax.inject.Inject

class AdWillBeShownDialogFragment
@Inject constructor() : BaseMainDialogFragment() {

    companion object {
        fun newInstance() = AdWillBeShownDialogFragment()

        const val TAG = "AdWillBeShownDialogFragment"
    }

    private var _binding: AdWillBeShownDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = AdWillBeShownDialogBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadAndShowAd()
    }

    private fun initViews() {
        binding.root.setOnClickListener { dismiss() }
    }

    private fun loadAndShowAd() {
        MyInterstitialAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_INTERSTITIAL_GAME)
            .apply {
                try {
                    load(requireContext()) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            activityViewModel.showGameInterstitialAd(this)
                            dismiss()
                        }, 5 * A_MINUTE_IN_MILLIS)
                    }
                } catch (_: Exception) {
                    dismiss()
                }
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        activityViewModel.shouldShowGameInterstitialAd = true
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}