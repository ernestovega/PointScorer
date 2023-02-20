package com.etologic.pointscorer.app.main.dialogs.player_settings_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.utils.AdsExtensions.load
import com.etologic.pointscorer.app.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.databinding.GamePlayerSettingsDialogFragmentBinding
import com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE
import com.google.android.gms.ads.AdView
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class PlayerSettingsMenuDialogFragment @Inject constructor() : DaggerDialogFragment() {

    companion object {
        const val TAG = "PlayerSettingsDialogFragment"
        const val KEY_INITIAL_COLOR = "key_initial_color"
        const val KEY_INITIAL_NAME = "key_initial_name"
        const val KEY_INITIAL_POINTS = "key_initial_points"
        const val KEY_IS_ONE_PLAYER_FRAGMENT = "key_is_one_player_game"
    }

    interface PlayerDialogListener {
        fun onColorChanged(color: Int)
        fun onNameChanged(name: String)
        fun onRestorePlayerPointsClicked()
        fun onRestoreAllPlayersPointsClicked()
    }

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
    private var _binding: GamePlayerSettingsDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private var initialPoints: Int? = null
    private var initialColor: Int? = null
    private var initialName: String? = null
    private var playerDialogListener: PlayerDialogListener? = null
    private var bannerAdView: AdView? = null

    internal fun setPlayerDialogListener(playerDialogListener: PlayerDialogListener?) {
        this.playerDialogListener = playerDialogListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GamePlayerSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fun initListeners() {

            with(binding) {

                btSettingMenuClose.setOnClickListener {
                    etSettingsMenuName.hideKeyboard()
                    dismiss()
                }

                etSettingsMenuName.doOnTextChanged { text, _, _, _ ->
                    val name = (text ?: "").toString()
                    initialName = name
                    playerDialogListener?.onNameChanged(name)
                }

                vSettingsMenuColorRed.setOnClickListener {
                    selectColor(redColor)
                    redColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }
                vSettingsMenuColorOrange.setOnClickListener {
                    selectColor(orangeColor)
                    orangeColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }
                vSettingsMenuColorYellow.setOnClickListener {
                    selectColor(yellowColor)
                    yellowColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }
                vSettingsMenuColorGreen.setOnClickListener {
                    selectColor(greenColor)
                    greenColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }
                vSettingsMenuColorBlue.setOnClickListener {
                    selectColor(blueColor)
                    blueColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }
                vSettingsMenuColorPurple.setOnClickListener {
                    selectColor(purpleColor)
                    purpleColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }
                vSettingsMenuColorPink.setOnClickListener {
                    selectColor(pinkColor)
                    pinkColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }
                vSettingsMenuColorWhite.setOnClickListener {
                    selectColor(whiteColor)
                    whiteColor?.let { color -> playerDialogListener?.onColorChanged(color) }
                }

                btSettingMenuRestorePoints.setOnClickListener {

                    fun askConfirmRestorePlayerPoints() {
                        var name = (binding.etSettingsMenuName.text ?: "").toString()
                        if (name.isBlank())
                            name = getString(R.string.your)
                        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                            .setTitle(R.string.are_you_sure)
                            .setMessage(
                                String.format(
                                    getString(R.string.restart_x_points_to_y),
                                    name,
                                    initialPoints
                                )
                            )
                            .setNegativeButton(R.string.cancel, null)
                            .setPositiveButton(R.string.ok) { _, _ ->
                                playerDialogListener?.onRestorePlayerPointsClicked()
                                dismiss()
                            }
                            .create()
                            .show()
                    }

                    etSettingsMenuName.hideKeyboard()
                    askConfirmRestorePlayerPoints()
                }

                btSettingMenuRestoreAllPoints.setOnClickListener {

                    fun askConfirmRestoreAllPlayersPoints() {
                        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                            .setTitle(R.string.are_you_sure)
                            .setMessage(
                                String.format(
                                    getString(R.string.this_will_restore_all_points_in_this_screen_to_),
                                    initialPoints
                                )
                            )
                            .setNegativeButton(R.string.cancel, null)
                            .setPositiveButton(R.string.ok) { _, _ ->
                                playerDialogListener?.onRestoreAllPlayersPointsClicked()
                                dismiss()
                            }
                            .create()
                            .show()
                    }

                    etSettingsMenuName.hideKeyboard()
                    askConfirmRestoreAllPlayersPoints()
                }
            }
        }

        fun initAd() {
            bannerAdView = context?.let {
                AdView(it).apply {
                    adUnitId = BuildConfig.ADMOB_ADUNIT_BANNER_SETTINGS_MENU
                    setAdSize(MEDIUM_RECTANGLE)
                }
            }
            bannerAdView?.let { binding.flSettingsMenuMediumRectangleAdContainer?.addView(it) }
        }

        super.onViewCreated(view, savedInstanceState)
        initValues()
        initName()
        selectColor(initialColor)
        initListeners()
        initAd()
    }

    override fun onStart() {
        super.onStart()
        bannerAdView?.load()
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
        orangeTransparentColor =
            ContextCompat.getColor(requireContext(), R.color.orange_transparent)
        yellowTransparentColor =
            ContextCompat.getColor(requireContext(), R.color.yellow_transparent)
        greenTransparentColor = ContextCompat.getColor(requireContext(), R.color.green_transparent)
        blueTransparentColor = ContextCompat.getColor(requireContext(), R.color.blue_transparent)
        purpleTransparentColor =
            ContextCompat.getColor(requireContext(), R.color.purple_transparent)
        pinkTransparentColor = ContextCompat.getColor(requireContext(), R.color.pink_transparent)
        blackTransparentColor = ContextCompat.getColor(requireContext(), R.color.black_transparent)
        arguments?.let { arguments ->
            with(arguments) {
                initialColor = whiteColor?.let { getInt(KEY_INITIAL_COLOR, it) }
                initialName = getString(KEY_INITIAL_NAME, getString(R.string.player_name))
                initialPoints = getInt(KEY_INITIAL_POINTS, 0)
                val isOnePlayerGame = getBoolean(KEY_IS_ONE_PLAYER_FRAGMENT, false)
                if (isOnePlayerGame) binding.btSettingMenuRestoreAllPoints.visibility = GONE
            }
        }
    }

    private fun initName() {
        binding.etSettingsMenuName.setText(initialName)
        initialColor?.let { binding.etSettingsMenuName.setTextColor(it) }
    }

    private fun selectColor(selectedColor: Int?) {

        fun setSettingsMenuNameColor(textColor: Int?, hintColor: Int?) {
            textColor?.let { binding.etSettingsMenuName.setTextColor(it) }
            hintColor?.let { binding.etSettingsMenuName.setHintTextColor(it) }
        }

        initialColor = selectedColor
        with(binding) {
            vSettingsMenuColorRed.isSelected = false
            vSettingsMenuColorOrange.isSelected = false
            vSettingsMenuColorYellow.isSelected = false
            vSettingsMenuColorGreen.isSelected = false
            vSettingsMenuColorBlue.isSelected = false
            vSettingsMenuColorPurple.isSelected = false
            vSettingsMenuColorPink.isSelected = false
            vSettingsMenuColorWhite.isSelected = false
            when (selectedColor) {
                redColor -> {
                    vSettingsMenuColorRed.isSelected = true
                    setSettingsMenuNameColor(redColor, redTransparentColor)
                }
                orangeColor -> {
                    vSettingsMenuColorOrange.isSelected = true
                    setSettingsMenuNameColor(orangeColor, orangeTransparentColor)
                }
                yellowColor -> {
                    vSettingsMenuColorYellow.isSelected = true
                    setSettingsMenuNameColor(yellowColor, yellowTransparentColor)
                }
                greenColor -> {
                    vSettingsMenuColorGreen.isSelected = true
                    setSettingsMenuNameColor(greenColor, greenTransparentColor)
                }
                blueColor -> {
                    vSettingsMenuColorBlue.isSelected = true
                    setSettingsMenuNameColor(blueColor, blueTransparentColor)
                }
                purpleColor -> {
                    vSettingsMenuColorPurple.isSelected = true
                    setSettingsMenuNameColor(purpleColor, purpleTransparentColor)
                }
                pinkColor -> {
                    vSettingsMenuColorPink.isSelected = true
                    setSettingsMenuNameColor(pinkColor, pinkTransparentColor)
                }
                else -> {
                    vSettingsMenuColorWhite.isSelected = true
                    setSettingsMenuNameColor(blackColor, blackTransparentColor)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isAdded) dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}