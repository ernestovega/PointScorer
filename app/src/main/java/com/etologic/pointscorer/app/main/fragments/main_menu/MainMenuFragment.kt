package com.etologic.pointscorer.app.main.fragments.main_menu

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.common.ads.MyRewardedAd
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.common.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.*
import com.etologic.pointscorer.app.main.base.BaseMainFragmentWithAds
import com.etologic.pointscorer.common.Constants.MAX_POINTS
import com.etologic.pointscorer.common.Constants.MIN_POINTS
import com.etologic.pointscorer.databinding.MainMenuFragmentBinding

class MainMenuFragment : BaseMainFragmentWithAds() {

    private var fragmentBinding: MainMenuFragmentBinding? = null
    private val binding get() = fragmentBinding!!

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
        loadAds()
    }

    private fun loadAds() {
        if (activityViewModel.shouldShowBannerAds) {
            if (activityViewModel.myRewardedAd == null) {
                with(MyRewardedAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_REWARDED_MAIN_MENU)) {
                    try {
                        load(requireContext()) {
                            activityViewModel.myRewardedAd = this
                            binding.acbMainMenuWatchAd?.text =
                                getString(R.string.remove_ads_watching_a_big_one)
                            binding.pbMainMenuWatchAdButton?.visibility = GONE
                            binding.acbMainMenuWatchAd?.visibility = VISIBLE
                        }
                    } catch (exception: MyBaseAd.AdCouldNotBeLoadedException) {
                        binding.acbMainMenuWatchAd?.text = getString(R.string.watch_an_ad_for_love)
                        binding.pbMainMenuWatchAdButton?.visibility = GONE
                        binding.acbMainMenuWatchAd?.visibility = VISIBLE
                    }
                }
            } else {
                binding.acbMainMenuWatchAd?.text =
                    getString(R.string.remove_ads_watching_a_big_one)
                binding.pbMainMenuWatchAdButton?.visibility = GONE
                binding.acbMainMenuWatchAd?.visibility = VISIBLE
            }
        } else {
            binding.acbMainMenuWatchAd?.text = getString(R.string.watch_an_ad_for_love)
            binding.pbMainMenuWatchAdButton?.visibility = GONE
            binding.acbMainMenuWatchAd?.visibility = VISIBLE
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

            fun gameButtonPressed(button: View, progress: View, screen: Screens) {
                button.visibility = GONE
                progress.visibility = VISIBLE
                activityViewModel.navigateTo(screen)
            }

            btMainMenu1Player.setOnClickListener {
                gameButtonPressed(btMainMenu1Player, pbMainMenu1Player, GAME_ONE_PLAYER)
            }
            btMainMenu2Player.setOnClickListener {
                gameButtonPressed(btMainMenu2Player, pbMainMenu2Player, GAME_TWO_PLAYERS)
            }
            btMainMenu3Player.setOnClickListener {
                gameButtonPressed(btMainMenu3Player, pbMainMenu3Player, GAME_THREE_PLAYERS)
            }
            btMainMenu4Player.setOnClickListener {
                gameButtonPressed(btMainMenu4Player, pbMainMenu4Player, GAME_FOUR_PLAYERS)
            }
            btMainMenu5Player.setOnClickListener {
                gameButtonPressed(btMainMenu5Player, btMainMenu5Player, GAME_FIVE_PLAYERS)
            }
            btMainMenu6Player.setOnClickListener {
                gameButtonPressed(btMainMenu6Player, pbMainMenu6Player, GAME_SIX_PLAYERS)
            }
            btMainMenu7Player.setOnClickListener {
                gameButtonPressed(btMainMenu7Player, pbMainMenu7Player, GAME_SEVEN_PLAYERS)
            }
            btMainMenu8Player.setOnClickListener {
                gameButtonPressed(btMainMenu8Player, pbMainMenu8Player, GAME_EIGHT_PLAYERS)
            }

            acbMainMenuWatchAd?.setOnClickListener {

                fun rewardedAdShown() {
                    activityViewModel.shouldShowBannerAds = false
                    binding.llAdsContainer.visibility = GONE
                    binding.acbMainMenuWatchAd?.text = getString(R.string.watch_an_ad_for_love)
                    loadNewInterstitialAdForShowLove()
                }

                if (activityViewModel.shouldShowBannerAds && activityViewModel.myRewardedAd != null) {
                    try {
                        activityViewModel.myRewardedAd!!.show(requireActivity()) {
                            Handler(Looper.getMainLooper()).postDelayed({ rewardedAdShown() }, 1000)
                        }
                    } catch (exception: MyBaseAd.AdCouldNotBeShownException) {
                        Toast.makeText(activity, getString(R.string.ups_but_thanks), LENGTH_SHORT)
                            .show()
                        rewardedAdShown()
                    }
                } else {
                    try {
                        activityViewModel.myInterstitialAdForShowLove?.show(requireActivity()) {
                            loadNewInterstitialAdForShowLove()
                        }
                    } catch (exception: MyBaseAd.AdCouldNotBeShownException) {
                        Toast.makeText(activity, getString(R.string.ups_but_thanks), LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun loadNewInterstitialAdForShowLove() {
        with(MyInterstitialAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_INTERSTITIAL_MAIN_MENU)) {
            try {
                load(requireContext()) { activityViewModel.myInterstitialAdForShowLove = this }
            } catch (_: MyBaseAd.AdCouldNotBeLoadedException) {
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
