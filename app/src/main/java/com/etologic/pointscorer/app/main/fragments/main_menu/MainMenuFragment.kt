package com.etologic.pointscorer.app.main.fragments.main_menu

import android.os.Bundle
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
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.*
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.fragments.main_menu.main_settings.MainSettingsMenuDialogFragment
import com.etologic.pointscorer.databinding.MainMenuFragmentBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainMenuFragment : BaseMainFragment() {

    private var fragmentBinding: MainMenuFragmentBinding? = null
    private val binding get() = fragmentBinding!!

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
        initListeners()
    }

    private fun initListeners() {
        with(binding) {

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

            acbMainMenuWatchAd.setOnClickListener {
                binding.acbMainMenuWatchAd.visibility = GONE
                binding.pbMainMenuWatchAdButton.visibility = VISIBLE
                with(MyInterstitialAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_MAIN_INTERSTITIAL)) {
                    try {
                        load(requireContext()) {
                            show(requireActivity()) {
                                binding.pbMainMenuWatchAdButton.visibility = GONE
                                binding.acbMainMenuWatchAd.visibility = VISIBLE
                            }
                        }
                    } catch (e: MyBaseAd.AdCouldNotBeLoadedException) {
                        binding.pbMainMenuWatchAdButton.visibility = GONE
                        binding.acbMainMenuWatchAd.visibility = VISIBLE
                        FirebaseCrashlytics.getInstance().recordException(e)
                        Toast.makeText(activity, getString(R.string.ups_but_thanks), LENGTH_SHORT).show()
                    }
                }
            }

            fabMainMenuSettings.setOnClickListener {
                MainSettingsMenuDialogFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, MainSettingsMenuDialogFragment.TAG)
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
        }
        super.onStop()
    }

}
