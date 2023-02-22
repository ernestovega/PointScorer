package com.etologic.pointscorer.app.main.fragments.main_menu

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.*
import com.etologic.pointscorer.app.main.base.BaseMainFragmentWithAds
import com.etologic.pointscorer.app.utils.MyAnimationUtils
import com.etologic.pointscorer.app.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.common.Constants.MAX_POINTS
import com.etologic.pointscorer.common.Constants.MIN_POINTS
import com.etologic.pointscorer.databinding.MainMenuFragmentBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class MainMenuFragment : BaseMainFragmentWithAds() {

    private var fragmentBinding: MainMenuFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    private var rewardedAd: RewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = MainMenuFragmentBinding.inflate(inflater, container, false)
        baseBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initListeners()
        loadRewardedAd()
    }

    private fun loadRewardedAd() {
        if (activityViewModel.shouldShowAds) {
            RewardedAd.load(
                requireContext(),
                BuildConfig.ADMOB_ADUNIT_REWARDED_MAIN_MENU,
                AdRequest.Builder().apply {
                    val extras = Bundle().apply { putString("npa", "1") }
                    addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
                }.build(),
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError.toString())
                        rewardedAd = null
                        binding.acbMainMenuWatchABigAd?.text = getString(R.string.watch_an_ad_for_love)
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d(TAG, "Ad was loaded.")
                        rewardedAd = ad
                        binding.acbMainMenuWatchABigAd?.text = getString(R.string.remove_ads_watching_a_big_one)
                        rewardedAd?.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdClicked() {
                                    Log.d(TAG, "Ad was clicked.")
                                }

                                override fun onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.
                                    Log.d(TAG, "Ad dismissed fullscreen content.")
                                    rewardedAd = null
                                }

                                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                    // Called when ad fails to show.
                                    Log.e(TAG, "Ad failed to show fullscreen content.")
                                    rewardedAd = null
                                }

                                override fun onAdImpression() {
                                    // Called when an impression is recorded for an ad.
                                    Log.d(TAG, "Ad recorded an impression.")
                                }

                                override fun onAdShowedFullScreenContent() {
                                    // Called when ad is shown.
                                    Log.d(TAG, "Ad showed fullscreen content.")
                                }
                            }
                    }
                }
            )
        } else {
            binding.acbMainMenuWatchABigAd?.text = getString(R.string.watch_an_ad_for_love)
        }
    }

    private fun initValues() {
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
                        var points = try {
                            p0.toString().toInt()
                        } catch (nfe: NumberFormatException) {
                            etMainMenuInitialPoints.error = getString(R.string.error_initial_points)
                            return
                        }
                        if (points > MAX_POINTS) {
                            points = MAX_POINTS
                            etMainMenuInitialPoints.error = getString(R.string.error_max_points)
                            etMainMenuInitialPoints.setText(points.toString())
                        } else if (points < MIN_POINTS) {
                            points = MIN_POINTS
                            etMainMenuInitialPoints.error = getString(R.string.error_min_points)
                            etMainMenuInitialPoints.setText(points.toString())
                        }
                        activityViewModel.saveInitialPoints(points)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            acbMainMenuResetAllPoints.setOnClickListener {
                etMainMenuInitialPoints?.hideKeyboard()
                with(AlertDialog.Builder(requireContext())) {
                    setTitle(R.string.are_you_sure)
                    setMessage(
                        getString(
                            R.string.this_will_restore_all_points_to_,
                            activityViewModel.initialPointsObservable.value
                        )
                    )
                    setPositiveButton(R.string.ok) { _, _ ->
                        activityViewModel.restoreAllGamesPoints()
                        Toast.makeText(
                            requireContext(),
                            R.string.all_players_points_were_restored,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    setNegativeButton(R.string.cancel) { _, _ -> }
                    show()
                }
            }

            acbMainMenuResetAllNamesAndColors.setOnClickListener {
                with(AlertDialog.Builder(requireContext())) {
                    setTitle(R.string.are_you_sure)
                    setMessage(getString(R.string.this_will_restore_all_names_and_colors_to))
                    setPositiveButton(R.string.ok) { _, _ ->
                        activityViewModel.restoreAllGamesNamesAndColors()
                        Toast.makeText(
                            requireContext(),
                            R.string.all_players_names_and_colors_were_restored,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    setNegativeButton(R.string.cancel) { _, _ -> }
                    show()
                }
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

            acbMainMenuWatchABigAd?.setOnClickListener {
                if (activityViewModel.shouldShowAds && rewardedAd != null) {
                    rewardedAd!!.show(requireActivity()) { rewardItem ->
                        // Handle the reward.
                        Log.d(TAG, "User earned the reward($rewardItem)")
                        activityViewModel.shouldShowAds = false
                        rewardedAd = null
                        binding.acbMainMenuWatchABigAd?.text = getString(R.string.watch_an_ad_for_love)
                        binding.llMainMenuAdsContainer.visibility = GONE
                    }
                } else {
                    //ToDO: show regular interstitial <3
                    Toast.makeText(
                        requireContext(),
                        "ToDo: Show regular interstitial <3",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

    companion object {
        private const val TAG = "MainActivity"
    }
}
