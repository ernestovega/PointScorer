package com.etologic.pointscorer.app.games.base.player

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.base.BaseMainDialogFragment
import com.etologic.pointscorer.app.common.ads.MyRobaAd
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeLoadedException
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeShownException
import com.etologic.pointscorer.app.common.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.app.games.base.game.GameViewModel
import com.etologic.pointscorer.databinding.PlayerSettingsDialogFragmentBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class PlayerSettingsMenuDialogFragment
@Inject constructor() : BaseMainDialogFragment() {

    private var redColor: Int? = null
    private var orangeColor: Int? = null
    private var yellowColor: Int? = null
    private var greenColor: Int? = null
    private var blueColor: Int? = null
    private var purpleColor: Int? = null
    private var pinkColor: Int? = null
    private var whiteColor: Int? = null
    private var blackColor: Int? = null
    private var redTransparentColor: Int? = null
    private var orangeTransparentColor: Int? = null
    private var yellowTransparentColor: Int? = null
    private var greenTransparentColor: Int? = null
    private var blueTransparentColor: Int? = null
    private var purpleTransparentColor: Int? = null
    private var pinkTransparentColor: Int? = null
    private var blackTransparentColor: Int? = null

    private val navigationArguments: PlayerSettingsMenuDialogFragmentArgs by navArgs()
    private var _binding: PlayerSettingsDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private val gameViewModel: GameViewModel by hiltNavGraphViewModels(R.id.a_main_nav_graph)
    private var playerId: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PlayerSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initListeners()
        initAd()
    }

    private fun initValues() {
        redColor = ContextCompat.getColor(requireContext(), R.color.red)
        orangeColor = ContextCompat.getColor(requireContext(), R.color.orange)
        yellowColor = ContextCompat.getColor(requireContext(), R.color.yellow)
        greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        blueColor = ContextCompat.getColor(requireContext(), R.color.blue)
        purpleColor = ContextCompat.getColor(requireContext(), R.color.purple)
        pinkColor = ContextCompat.getColor(requireContext(), R.color.pink)
        whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
        blackColor = ContextCompat.getColor(requireContext(), R.color.black)
        redTransparentColor = ContextCompat.getColor(requireContext(), R.color.red_transparent)
        orangeTransparentColor = ContextCompat.getColor(requireContext(), R.color.orange_transparent)
        yellowTransparentColor = ContextCompat.getColor(requireContext(), R.color.yellow_transparent)
        greenTransparentColor = ContextCompat.getColor(requireContext(), R.color.green_transparent)
        blueTransparentColor = ContextCompat.getColor(requireContext(), R.color.blue_transparent)
        purpleTransparentColor = ContextCompat.getColor(requireContext(), R.color.purple_transparent)
        pinkTransparentColor = ContextCompat.getColor(requireContext(), R.color.pink_transparent)
        blackTransparentColor = ContextCompat.getColor(requireContext(), R.color.black_transparent)

        playerId = navigationArguments.playerId
        binding.etPlayerSettingsMenuName.setText(navigationArguments.playerInitialName)
        selectColor(navigationArguments.playerInitialColor)
        playerBackgroundObserver(navigationArguments.playerHasCustomBackground)
    }

    private fun playerBackgroundObserver(hasBackground: Boolean) {
        with(binding) {
            btSettingMenuChangeBackground.apply {
                text = getString(if (hasBackground) R.string.restore_background else R.string.change_background)
                setOnClickListener {
                    etPlayerSettingsMenuName.hideKeyboard()
                    if (hasBackground) {
                        gameViewModel.restorePlayerBackground(playerId!!)
                    } else {
                        gameViewModel.changePlayerBackground(playerId!!)
                    }
                    dismiss()
                }
            }
        }
    }

    private fun initListeners() {
        with(binding) {

            btPlayerSettingsMenuClose.setOnClickListener {
                dismiss()
            }

            etPlayerSettingsMenuName.doAfterTextChanged { text ->
                gameViewModel.changePlayerName(playerId!!, text.toString())
            }

            fun savePlayerColor(color: Int) {
                gameViewModel.changePlayerColor(playerId!!, color)
                selectColor(color)
            }
            vPlayerSettingsMenuColorRed.setOnClickListener { savePlayerColor(redColor!!) }
            vPlayerSettingsMenuColorOrange.setOnClickListener { savePlayerColor(orangeColor!!) }
            vPlayerSettingsMenuColorYellow.setOnClickListener { savePlayerColor(yellowColor!!) }
            vPlayerSettingsMenuColorGreen.setOnClickListener { savePlayerColor(greenColor!!) }
            vPlayerSettingsMenuColorBlue.setOnClickListener { savePlayerColor(blueColor!!) }
            vPlayerSettingsMenuColorPurple.setOnClickListener { savePlayerColor(purpleColor!!) }
            vPlayerSettingsMenuColorPink.setOnClickListener { savePlayerColor(pinkColor!!) }
            vPlayerSettingsMenuColorWhite.setOnClickListener { savePlayerColor(whiteColor!!) }

            btPlayerSettingsMenuRestorePoints.setOnClickListener {

                fun askConfirmRestorePlayerPoints() {
                    var name = (etPlayerSettingsMenuName.text ?: "").toString()
                    if (name.isBlank()) {
                        name = getString(R.string.your)
                    }
                    AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle(R.string.are_you_sure)
                        .setMessage(getString(R.string.restart_x_points_to_y, name, gameViewModel.initialPoints))
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.ok) { _, _ -> gameViewModel.restorePlayerPoints(playerId!!) }
                        .create()
                        .show()
                }

                etPlayerSettingsMenuName.hideKeyboard()
                askConfirmRestorePlayerPoints()
            }

        }
    }

    private fun initAd() {
        with(MyRobaAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_ROBA, requireContext())) {
            try {
                load(requireContext()) {
                    with(binding.llPlayerSettingsMenuMediumRectangleAdContainer) {
                        visibility = try {
                            show(this)
                            VISIBLE
                        } catch (e: AdCouldNotBeShownException) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                            GONE
                        }
                    }
                }
            } catch (e: AdCouldNotBeLoadedException) {
                FirebaseCrashlytics.getInstance().recordException(e)
                binding.llPlayerSettingsMenuMediumRectangleAdContainer.visibility = GONE
            }
        }
    }

    private fun selectColor(selectedColor: Int?) {
        with(binding) {
            etPlayerSettingsMenuName.hideKeyboard()
            vPlayerSettingsMenuColorRed.isSelected = false
            vPlayerSettingsMenuColorOrange.isSelected = false
            vPlayerSettingsMenuColorYellow.isSelected = false
            vPlayerSettingsMenuColorGreen.isSelected = false
            vPlayerSettingsMenuColorBlue.isSelected = false
            vPlayerSettingsMenuColorPurple.isSelected = false
            vPlayerSettingsMenuColorPink.isSelected = false
            vPlayerSettingsMenuColorWhite.isSelected = false

            fun EditText.setTextsColors(textColor: Int, hintColor: Int) {
                setTextColor(textColor)
                setHintTextColor(hintColor)
            }

            when (selectedColor) {
                redColor -> {
                    vPlayerSettingsMenuColorRed.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(redColor!!, redTransparentColor!!)
                }
                orangeColor -> {
                    vPlayerSettingsMenuColorOrange.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(orangeColor!!, orangeTransparentColor!!)
                }
                yellowColor -> {
                    vPlayerSettingsMenuColorYellow.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(yellowColor!!, yellowTransparentColor!!)
                }
                greenColor -> {
                    vPlayerSettingsMenuColorGreen.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(greenColor!!, greenTransparentColor!!)
                }
                blueColor -> {
                    vPlayerSettingsMenuColorBlue.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(blueColor!!, blueTransparentColor!!)
                }
                purpleColor -> {
                    vPlayerSettingsMenuColorPurple.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(purpleColor!!, purpleTransparentColor!!)
                }
                pinkColor -> {
                    vPlayerSettingsMenuColorPink.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(pinkColor!!, pinkTransparentColor!!)
                }
                else -> {
                    vPlayerSettingsMenuColorWhite.isSelected = true
                    etPlayerSettingsMenuName.setTextsColors(blackColor!!, blackTransparentColor!!)
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        gameViewModel.shouldShowGameInterstitialAd = true
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        binding.etPlayerSettingsMenuName.hideKeyboard()
        _binding = null
        super.onDestroyView()
    }

}