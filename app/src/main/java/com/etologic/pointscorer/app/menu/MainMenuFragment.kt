package com.etologic.pointscorer.app.menu

import android.os.Bundle
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.base.BaseMainFragmentWithAds
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeLoadedException
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame1PlayersFragment
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame2PlayersFragment
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame3PlayersFragment
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame4PlayersFragment
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame5PlayersFragment
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame6PlayersFragment
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame7PlayersFragment
import com.etologic.pointscorer.app.menu.MainMenuFragmentDirections.Companion.actionMainMenuFragmentToGame8PlayersFragment
import com.etologic.pointscorer.databinding.MainMenuFragmentBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainMenuFragment : BaseMainFragmentWithAds() {

    private var _binding: MainMenuFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainMenuFragmentBinding.inflate(inflater, container, false)
        baseBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun getAdsContainer(): LinearLayout = binding.llAdsContainer

    private fun initListeners() {
        with(binding) {

            fun gameButtonPressed(button: View, progress: View, action: NavDirections) {
                button.visibility = GONE
                progress.visibility = VISIBLE
                findNavController().navigate(action)
            }

            btMainMenu1Player.setOnClickListener {
                gameButtonPressed(btMainMenu1Player, pbMainMenu1Player, actionMainMenuFragmentToGame1PlayersFragment())
            }
            btMainMenu2Player.setOnClickListener {
                gameButtonPressed(btMainMenu2Player, pbMainMenu2Player, actionMainMenuFragmentToGame2PlayersFragment())
            }
            btMainMenu3Player.setOnClickListener {
                gameButtonPressed(btMainMenu3Player, pbMainMenu3Player, actionMainMenuFragmentToGame3PlayersFragment())
            }
            btMainMenu4Player.setOnClickListener {
                gameButtonPressed(btMainMenu4Player, pbMainMenu4Player, actionMainMenuFragmentToGame4PlayersFragment())
            }
            btMainMenu5Player.setOnClickListener {
                gameButtonPressed(btMainMenu5Player, pbMainMenu5Player, actionMainMenuFragmentToGame5PlayersFragment())
            }
            btMainMenu6Player.setOnClickListener {
                gameButtonPressed(btMainMenu6Player, pbMainMenu6Player, actionMainMenuFragmentToGame6PlayersFragment())
            }
            btMainMenu7Player.setOnClickListener {
                gameButtonPressed(btMainMenu7Player, pbMainMenu7Player, actionMainMenuFragmentToGame7PlayersFragment())
            }
            btMainMenu8Player.setOnClickListener {
                gameButtonPressed(btMainMenu8Player, pbMainMenu8Player, actionMainMenuFragmentToGame8PlayersFragment())
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
                                activityViewModel.shouldShowBannerAds = false
                                if (llAdsContainer.visibility == VISIBLE) {
                                    Toast.makeText(requireContext(), R.string.thanks_and_reward, LENGTH_SHORT).show()
                                    lifecycleScope.launch {
                                        delay(2 * SECOND_IN_MILLIS)
                                        findNavController().popBackStack() //To avoid duplicity on the BackStack
                                        findNavController().navigate(
                                            MainMenuFragmentDirections.actionMainMenuFragmentSelf()
                                        )
                                    }
                                } else {
                                    Toast.makeText(requireContext(), R.string.thanks, LENGTH_SHORT).show()
                                }
                            }
                        }
                    } catch (e: AdCouldNotBeLoadedException) {
                        binding.pbMainMenuWatchAdButton.visibility = GONE
                        binding.acbMainMenuWatchAd.visibility = VISIBLE
                        FirebaseCrashlytics.getInstance().recordException(e)
                        Toast.makeText(activity, getString(R.string.ups_but_thanks), LENGTH_SHORT).show()
                    }
                }
            }

            fabMainMenuSettings.setOnClickListener {
                findNavController().navigate(
                    MainMenuFragmentDirections.actionMainMenuFragmentToMainSettingsMenuDialogFragment()
                )
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

    override fun onDestroy() {
        lifecycleScope.coroutineContext.cancel()
        _binding = null
        super.onDestroy()
    }

}
