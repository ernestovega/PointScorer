package com.etologic.pointscorer.app.main.dialogs

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.GAME_INTERSTITIAL
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.databinding.AdWillAppearDialogBinding
import javax.inject.Inject

class ShowAdCountDownDialogFragment
@Inject constructor() : BaseMainDialogFragment() {

    companion object {
        const val TAG = "ShowAdCountDownDialogFragment"
        private const val PROGRESS_MAX = 100
        private const val PROGRESS_STEP = 1L
        private const val COUNTDOWN_DURATION = 5000L
        private const val COUNTDOWN_INTERVAL = COUNTDOWN_DURATION / PROGRESS_MAX
        private const val SECOND_IN_MILLIS = 1000
    }

    private var _binding: AdWillAppearDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adCountDownTimer: CountDownTimer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = AdWillAppearDialogBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initCountDownTimer()
    }

    private fun initViews() {
        with(binding) {
            tvAdWillAppearTitle.text =
                getString(R.string.an_ad_will_appear_in_x, COUNTDOWN_DURATION / SECOND_IN_MILLIS)
            lpiAdWillAppearSeconds.max = PROGRESS_MAX
            root.setOnClickListener { dismiss() }
        }
    }

    private fun initCountDownTimer() {
        with(binding) {
            adCountDownTimer = object : CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {

                override fun onTick(millisUntilFinished: Long) {
                    lpiAdWillAppearSeconds.progress =
                        lpiAdWillAppearSeconds.progress.minus(PROGRESS_STEP.toInt())
                    tvAdWillAppearTitle.text =
                        getString(R.string.an_ad_will_appear_in_x, ((millisUntilFinished / SECOND_IN_MILLIS) + 1))
                }

                override fun onFinish() {
                    tvAdWillAppearTitle.text = getString(R.string.an_ad_will_appear_in_x, 0)
                    lpiAdWillAppearSeconds.progress = 0
                    Handler(Looper.getMainLooper()).postDelayed({
                        activityViewModel.navigateTo(GAME_INTERSTITIAL)
                        dismiss()
                    }, 100)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        activityViewModel.canRestartTheCountDownToShowAd = false
        activityViewModel.cancelHandlerCountDownToShowGameAd()
        adCountDownTimer.start()
    }

    override fun onPause() {
        adCountDownTimer.cancel()
        activityViewModel.avoidRestartTheCountDownToShowGameAdDuringAuxPointsAnimationDuration()
        super.onPause()
    }

}