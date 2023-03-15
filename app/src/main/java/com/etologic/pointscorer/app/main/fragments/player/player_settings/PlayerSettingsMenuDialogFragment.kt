package com.etologic.pointscorer.app.main.fragments.player.player_settings

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.ads.MyRobaAd
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeLoadedException
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeShownException
import com.etologic.pointscorer.app.common.exceptions.ArgumentNotFoundException
import com.etologic.pointscorer.app.common.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragmentViewModel
import com.etologic.pointscorer.databinding.GamePlayerSettingsDialogFragmentBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class PlayerSettingsMenuDialogFragment
@Inject constructor() : BaseMainDialogFragment() {

    companion object {
        const val KEY_PLAYER_ID = "KEY_PLAYER_ID"
        const val KEY_PARENT_FRAGMENT_ID = "KEY_PARENT_FRAGMENT_ID"
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
    private val viewModel: PlayerSettingsMenuDialogViewModel by viewModels()
    private lateinit var parentViewModel: PlayerFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GamePlayerSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initObservers()
        initListeners()
        initAd()
    }

    private fun initValues() {
        val playerId = arguments?.getInt(KEY_PLAYER_ID)
        @IdRes val parentFragmentId = arguments?.getInt(KEY_PARENT_FRAGMENT_ID)
        if (playerId == null || parentFragmentId == null) {
            FirebaseCrashlytics.getInstance().recordException(ArgumentNotFoundException())
            Toast.makeText(requireContext(), getString(R.string.ups), Toast.LENGTH_SHORT).show()
            dismiss()
        } else {
            val delegatedParentViewModel: PlayerFragmentViewModel by navGraphViewModels(parentFragmentId)
            parentViewModel = delegatedParentViewModel
            viewModel.playerId = playerId
        }

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
    }

    private fun initObservers() {
        parentViewModel.playerNameObservable().observe(viewLifecycleOwner) { playerNameObserver(it) }
        parentViewModel.playerColorObservable().observe(viewLifecycleOwner) { playerColorObserver(it) }
        parentViewModel.playerBackgroundObservable().observe(viewLifecycleOwner) { playerBackgroundObserver(it) }

        viewModel.playerNameChangedObservable().observe(viewLifecycleOwner) { playerNameChangedObserver() }
        viewModel.playerColorChangedObservable().observe(viewLifecycleOwner) { playerColorChangedObserver() }
        viewModel.playerPointsRestoredObservable().observe(viewLifecycleOwner) { playerPointsRestoredObserver() }
    }

    private fun playerNameObserver(name: String) {
        binding.etSettingsMenuName.setText(name)
        parentViewModel.playerNameObservable().removeObservers(viewLifecycleOwner)
    }

    private fun playerColorObserver(color: Int) {
        selectColor(color)
    }

    private fun playerBackgroundObserver(bgUri: Uri?) {
        with(binding) {
            btSettingMenuChangeBackground.apply {
                text = getString(if (bgUri == null) R.string.change_background else R.string.restore_background)
                setOnClickListener {
                    etSettingsMenuName.hideKeyboard()
                    if (bgUri == null) {
                        parentViewModel.changeBackgroundRequested()
                    } else {
                        etSettingsMenuName.hideKeyboard()
                        parentViewModel.savePlayerBackground(null)
                    }
                }
            }
        }
    }

    private fun playerNameChangedObserver() {
        parentViewModel.loadPlayerName()
    }

    private fun playerColorChangedObserver() {
        parentViewModel.loadPlayerColor()
    }

    private fun playerPointsRestoredObserver() {
        parentViewModel.loadPlayerPoints()
    }

    private fun initListeners() {
        with(binding) {

            btSettingMenuClose.setOnClickListener {
                dismiss()
            }

            etSettingsMenuName.doAfterTextChanged { text -> viewModel.savePlayerName((text ?: "").toString()) }

            vSettingsMenuColorRed.setOnClickListener { viewModel.savePlayerColor(redColor!!) }
            vSettingsMenuColorOrange.setOnClickListener { viewModel.savePlayerColor(orangeColor!!) }
            vSettingsMenuColorYellow.setOnClickListener { viewModel.savePlayerColor(yellowColor!!) }
            vSettingsMenuColorGreen.setOnClickListener { viewModel.savePlayerColor(greenColor!!) }
            vSettingsMenuColorBlue.setOnClickListener { viewModel.savePlayerColor(blueColor!!) }
            vSettingsMenuColorPurple.setOnClickListener { viewModel.savePlayerColor(purpleColor!!) }
            vSettingsMenuColorPink.setOnClickListener { viewModel.savePlayerColor(pinkColor!!) }
            vSettingsMenuColorWhite.setOnClickListener { viewModel.savePlayerColor(whiteColor!!) }

            btSettingMenuRestorePoints.setOnClickListener {

                fun askConfirmRestorePlayerPoints() {
                    var name = (binding.etSettingsMenuName.text ?: "").toString()
                    if (name.isBlank()) {
                        name = getString(R.string.your)
                    }
                    AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle(R.string.are_you_sure)
                        .setMessage(getString(R.string.restart_x_points_to_y, name, viewModel.initialPoints))
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.ok) { _, _ -> viewModel.restorePlayerPoints() }
                        .create()
                        .show()
                }

                etSettingsMenuName.hideKeyboard()
                askConfirmRestorePlayerPoints()
            }

        }
    }

    private fun initAd() {
        with(MyRobaAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_ROBA, requireContext())) {
            try {
                load(requireContext()) {
                    with(binding.llSettingsMenuMediumRectangleAdContainer) {
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
                binding.llSettingsMenuMediumRectangleAdContainer.visibility = GONE
            }
        }
    }

    private fun selectColor(selectedColor: Int?) {
        with(binding) {
            etSettingsMenuName.hideKeyboard()
            vSettingsMenuColorRed.isSelected = false
            vSettingsMenuColorOrange.isSelected = false
            vSettingsMenuColorYellow.isSelected = false
            vSettingsMenuColorGreen.isSelected = false
            vSettingsMenuColorBlue.isSelected = false
            vSettingsMenuColorPurple.isSelected = false
            vSettingsMenuColorPink.isSelected = false
            vSettingsMenuColorWhite.isSelected = false

            fun EditText.setTextsColors(textColor: Int, hintColor: Int) {
                setTextColor(textColor)
                setHintTextColor(hintColor)
            }

            when (selectedColor) {
                redColor -> {
                    vSettingsMenuColorRed.isSelected = true
                    etSettingsMenuName.setTextsColors(redColor!!, redTransparentColor!!)
                }
                orangeColor -> {
                    vSettingsMenuColorOrange.isSelected = true
                    etSettingsMenuName.setTextsColors(orangeColor!!, orangeTransparentColor!!)
                }
                yellowColor -> {
                    vSettingsMenuColorYellow.isSelected = true
                    etSettingsMenuName.setTextsColors(yellowColor!!, yellowTransparentColor!!)
                }
                greenColor -> {
                    vSettingsMenuColorGreen.isSelected = true
                    etSettingsMenuName.setTextsColors(greenColor!!, greenTransparentColor!!)
                }
                blueColor -> {
                    vSettingsMenuColorBlue.isSelected = true
                    etSettingsMenuName.setTextsColors(blueColor!!, blueTransparentColor!!)
                }
                purpleColor -> {
                    vSettingsMenuColorPurple.isSelected = true
                    etSettingsMenuName.setTextsColors(purpleColor!!, purpleTransparentColor!!)
                }
                pinkColor -> {
                    vSettingsMenuColorPink.isSelected = true
                    etSettingsMenuName.setTextsColors(pinkColor!!, pinkTransparentColor!!)
                }
                else -> {
                    vSettingsMenuColorWhite.isSelected = true
                    etSettingsMenuName.setTextsColors(blackColor!!, blackTransparentColor!!)
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        binding.etSettingsMenuName.hideKeyboard()
        activityViewModel.shouldShowGameInterstitialAd = true
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}