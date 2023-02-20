package com.etologic.pointscorer.app.main.dialogs.restore_all_points_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.app.utils.AdsExtensions.load
import com.etologic.pointscorer.databinding.RestoreAllPointsDialogFragmentBinding
import com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE
import com.google.android.gms.ads.AdView
import javax.inject.Inject

class RestoreAllPointsDialogFragment @Inject constructor() : BaseMainDialogFragment() {

    companion object {
        const val TAG = "RestoreAllPointsDialogFragment"
    }

    private var fragmentBinding: RestoreAllPointsDialogFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    private var bannerAdView: AdView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = RestoreAllPointsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fun initMessage() {
            binding.tvRestoreAllPointsMessage.text = getString(
                R.string.this_will_restore_all_points_to_,
                activityViewModel.initialPointsObservable.value
            )
        }

        fun initListeners() {
            with(binding) {
                btRestoreAllPointsOk.setOnClickListener {
                    activityViewModel.restoreAllGamesPoints()
                    Toast.makeText(
                        requireContext(),
                        R.string.all_players_points_were_restored,
                        Toast.LENGTH_LONG
                    ).show()
                    dismiss()
                }
                btRestoreAllPointsCancel.setOnClickListener {
                    dismiss()
                }
            }
        }

        fun initAd() {
            bannerAdView = context?.let {
                AdView(it).apply {
                    adUnitId = BuildConfig.ADMOB_ADUNIT_BANNER_RESTORE_ALL_POINTS_MENU
                    setAdSize(MEDIUM_RECTANGLE)
                }
            }
            bannerAdView?.let { binding.flRestoreAllPointsMediumRectangleAdContainer.addView(it) }
        }

        super.onViewCreated(view, savedInstanceState)
        initMessage()
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
