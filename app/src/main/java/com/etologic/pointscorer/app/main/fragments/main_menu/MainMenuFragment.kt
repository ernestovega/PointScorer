package com.etologic.pointscorer.app.main.fragments.main_menu

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.*
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.dialogs.restore_all_points_dialog.RestoreAllPointsDialogFragment
import com.etologic.pointscorer.app.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.app.utils.dpToPx
import com.etologic.pointscorer.databinding.MainMenuFragmentBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize.BANNER
import com.google.android.gms.ads.AdView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class MainMenuFragment : BaseMainFragment() {

    companion object {
        val adUnitsList = listOf(
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_1,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_2,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_3,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_4,
            BuildConfig.ADMOB_ADUNIT_BANNER_MAIN_MENU_5,
        )
    }

    @Inject
    lateinit var restoreAllPointsDialogFragment: RestoreAllPointsDialogFragment
    private var fragmentBinding: MainMenuFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    private var errorInitialPointsLiteral: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = MainMenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAds()
        initValues()
        initListeners()
    }

    private fun initAds() {

        fun getAdUnitsForThisScreen(): List<String> {
            val adUnitsListForThisScreen = mutableListOf<String>()
            var numBannersFittingThisScreenWidth =
                resources.displayMetrics.widthPixels / BANNER.width.dpToPx(resources)
            if (numBannersFittingThisScreenWidth > adUnitsList.size) {
                FirebaseCrashlytics.getInstance()
                    .setCustomKey("MoreThan6BannersCouldBeAdded", numBannersFittingThisScreenWidth)
                numBannersFittingThisScreenWidth = adUnitsList.size
            }
            for (i in 0 until numBannersFittingThisScreenWidth) adUnitsListForThisScreen.add(
                adUnitsList[i]
            )
            return adUnitsListForThisScreen
        }

        fun buildBannerAd(adUnit: String): AdView? =
            context?.let {
                AdView(it).apply {
                    adUnitId = adUnit
                    setAdSize(BANNER)
                }
            }

        fun loadAd(adView: AdView) {
            val adRequest = AdRequest.Builder().apply {
                val extras = Bundle().apply { putString("npa", "1") }
                addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            }.build()
            adView.loadAd(adRequest)
        }

        getAdUnitsForThisScreen().forEach { adUnit ->
            buildBannerAd(adUnit)?.let { bannerAdView ->
                binding.llMainMenuAdsContainer.addView(bannerAdView)
                loadAd(bannerAdView)
            }
        }
    }

    private fun initValues() {
        errorInitialPointsLiteral = getString(R.string.error_initial_points)
        activityViewModel.initialPointsObservable.observe(viewLifecycleOwner) {
            binding.etMainMenuInitialPoints?.setText(it.toString())
            activityViewModel.initialPointsObservable.removeObservers(viewLifecycleOwner)
        }
        activityViewModel.getInitialPoints()
    }

    private fun initListeners() {
        with(binding) {
            tvMainMenuInitialPointsTitle.setOnClickListener {
                AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle(R.string.initial_points)
                    .setMessage(R.string.initial_points_help)
                    .setPositiveButton(R.string.ok) { _, _ -> etMainMenuInitialPoints?.hideKeyboard() }
                    .create()
                    .show()
            }

            etMainMenuInitialPoints?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        try {
                            activityViewModel.saveInitialPoints(p0.toString().toInt())
                        } catch (nfe: NumberFormatException) {
                            etMainMenuInitialPoints.error = errorInitialPointsLiteral
                        }
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            acbMainMenuResetAllPoints.setOnClickListener {
                etMainMenuInitialPoints?.hideKeyboard()
                restoreAllPointsDialogFragment.show(
                    parentFragmentManager,
                    RestoreAllPointsDialogFragment.TAG
                )
            }

            btMainMenu1Player.setOnClickListener {
                btMainMenu1Player.visibility = GONE
                pbMainMenu1Player.visibility = VISIBLE
                activityViewModel.navigateTo(ONE_PLAYER)
            }
            btMainMenu2Player.setOnClickListener {
                btMainMenu2Player.visibility = GONE
                pbMainMenu2Player.visibility = VISIBLE
                activityViewModel.navigateTo(TWO_PLAYER)
            }
            btMainMenu3Player.setOnClickListener {
                btMainMenu3Player.visibility = GONE
                pbMainMenu3Player.visibility = VISIBLE
                activityViewModel.navigateTo(THREE_PLAYER)
            }
            btMainMenu4Player.setOnClickListener {
                btMainMenu4Player.visibility = GONE
                pbMainMenu4Player.visibility = VISIBLE
                activityViewModel.navigateTo(FOUR_PLAYER)
            }
            btMainMenu5Player.setOnClickListener {
                btMainMenu5Player.visibility = GONE
                pbMainMenu5Player.visibility = VISIBLE
                activityViewModel.navigateTo(FIVE_PLAYER)
            }
            btMainMenu6Player.setOnClickListener {
                btMainMenu6Player.visibility = GONE
                pbMainMenu6Player.visibility = VISIBLE
                activityViewModel.navigateTo(SIX_PLAYER)
            }
            btMainMenu7Player.setOnClickListener {
                btMainMenu7Player.visibility = GONE
                pbMainMenu7Player.visibility = VISIBLE
                activityViewModel.navigateTo(SEVEN_PLAYER)
            }
            btMainMenu8Player.setOnClickListener {
                btMainMenu8Player.visibility = GONE
                pbMainMenu8Player.visibility = VISIBLE
                activityViewModel.navigateTo(EIGHT_PLAYER)
            }
        }
    }

    override fun onStop() {
        with(binding) {
            pbMainMenu1Player.visibility = GONE
            pbMainMenu2Player.visibility = GONE
            pbMainMenu3Player.visibility = GONE
            pbMainMenu4Player.visibility = GONE
            pbMainMenu5Player.visibility = GONE
            pbMainMenu6Player.visibility = GONE
            pbMainMenu7Player.visibility = GONE
            pbMainMenu8Player.visibility = GONE
            btMainMenu1Player.visibility = VISIBLE
            btMainMenu2Player.visibility = VISIBLE
            btMainMenu3Player.visibility = VISIBLE
            btMainMenu4Player.visibility = VISIBLE
            btMainMenu5Player.visibility = VISIBLE
            btMainMenu6Player.visibility = VISIBLE
            btMainMenu7Player.visibility = VISIBLE
            btMainMenu8Player.visibility = VISIBLE
            etMainMenuInitialPoints?.hideKeyboard()
        }
        super.onStop()
    }
}
