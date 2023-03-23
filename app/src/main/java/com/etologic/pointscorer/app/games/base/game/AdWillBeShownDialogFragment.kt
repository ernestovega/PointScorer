package com.etologic.pointscorer.app.games.base.game

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.app.base.BaseMainDialogFragment
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.common.Constants.A_SECOND_IN_MILLIS
import com.etologic.pointscorer.databinding.AdWillBeShownDialogBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdWillBeShownDialogFragment
@Inject constructor() : BaseMainDialogFragment() {

    private var _binding: AdWillBeShownDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameViewModel: com.etologic.pointscorer.app.games.base.game.GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = AdWillBeShownDialogBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initViews()
        loadAndShowAd()
    }

    private fun initValues() {
        val delegatedGameViewModel: com.etologic.pointscorer.app.games.base.game.GameViewModel by viewModels()
        gameViewModel = delegatedGameViewModel
    }

    private fun initViews() {
        binding.root.setOnClickListener { dismiss() }
    }

    private fun loadAndShowAd() {
        MyInterstitialAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_GAME_INTERSTITIAL)
            .apply {
                try {
                    load(requireContext()) {
                        lifecycleScope.launch {
                            if (BuildConfig.DEBUG) {
                                delay(4 * A_SECOND_IN_MILLIS)
                            }
                            show(requireActivity())
                            dismiss()
                        }
                    }
                } catch (e: Exception) {
                    FirebaseCrashlytics.getInstance().recordException(e)
                    dismiss()
                }
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        gameViewModel.shouldShowGameInterstitialAd = true
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}