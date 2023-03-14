package com.etologic.pointscorer.app.main.fragments.player.player_settings

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.ads.MyRobaAd
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeLoadedException
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeShownException
import com.etologic.pointscorer.app.common.exceptions.PlayerIdNotFoundException
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
    private val parentViewModel: PlayerFragmentViewModel by viewModels()

    private val cropImageActivityResultLauncher = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful && result.uriContent != null) {
            viewModel.savePlayerBackground(result.uriContent!!)
        } else {
            FirebaseCrashlytics.getInstance().recordException(Throwable(result.error))
            Toast.makeText(requireContext(), getString(R.string.ups), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GamePlayerSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initViewModel()
        loadScreen()
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
    }

    private fun initViewModel() {
        viewModel.playerNameObservable().observe(viewLifecycleOwner) { playerNameObserver(it) }
        viewModel.playerColorObservable().observe(viewLifecycleOwner) { playerColorObserver(it) }
        viewModel.playerBackgroundObservable().observe(viewLifecycleOwner) { playerBackgroundObserver(it) }
        viewModel.playerNameChangedObservable().observe(viewLifecycleOwner) { playerNameChangedObserver(it) }
        viewModel.playerColorChangedObservable().observe(viewLifecycleOwner) { playerColorChangedObserver(it) }
        viewModel.playerBackgroundChangedObservable()
            .observe(viewLifecycleOwner) { playerBackgroundChangedObserver(it) }
        viewModel.playerPointsRestoredObservable().observe(viewLifecycleOwner) { playerPointsRestoredObserver() }
    }

    private fun playerNameObserver(name: String) {
        binding.etSettingsMenuName.setText(name)
    }

    private fun playerColorObserver(color: Int) {
        selectColor(color)
    }

    private fun selectColor(selectedColor: Int?) {
        with(binding) {
            vSettingsMenuColorRed.isSelected = false
            vSettingsMenuColorOrange.isSelected = false
            vSettingsMenuColorYellow.isSelected = false
            vSettingsMenuColorGreen.isSelected = false
            vSettingsMenuColorBlue.isSelected = false
            vSettingsMenuColorPurple.isSelected = false
            vSettingsMenuColorPink.isSelected = false
            vSettingsMenuColorWhite.isSelected = false

            fun setSettingsMenuNameColor(textColor: Int?, hintColor: Int?) {
                binding.etSettingsMenuName.apply {
                    textColor?.let { setTextColor(it) }
                    hintColor?.let { setHintTextColor(it) }
                }
            }

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

    private fun playerBackgroundObserver(bgUri: Uri?) {
        binding.btSettingMenuChangeBackground.apply {
            if (bgUri == null) {
                text = getString(R.string.change_background)
                setOnClickListener {
                    cropImageActivityResultLauncher.launch(
                        options {
                            setImageSource(includeGallery = true, includeCamera = false)
                            setGuidelines(CropImageView.Guidelines.ON)
                        }
                    )
                }
            } else {
                text = getString(R.string.restore_background)
                setOnClickListener {
                    viewModel.savePlayerBackground(null)
                }
            }
        }
    }

    private fun playerNameChangedObserver(name: String) {
        playerNameObserver(name)
        parentViewModel.loadPlayerName()
    }

    private fun playerColorChangedObserver(color: Int) {
        playerColorObserver(color)
        parentViewModel.loadPlayerColor()
    }

    private fun playerBackgroundChangedObserver(bgUri: Uri?) {
        playerBackgroundObserver(bgUri)
        parentViewModel.loadPlayerBackground()
    }

    private fun playerPointsRestoredObserver() {
        parentViewModel.loadPlayerPoints()
    }

    private fun loadScreen() {
        arguments?.getInt(KEY_PLAYER_ID)
            ?.let { viewModel.loadScreen(it) }
            ?: run {
                FirebaseCrashlytics.getInstance().recordException(PlayerIdNotFoundException())
                Toast.makeText(requireContext(), getString(R.string.ups), Toast.LENGTH_SHORT).show()
                dismiss()
            }
    }

    private fun initListeners() {
        with(binding) {

            btSettingMenuClose.setOnClickListener {
                etSettingsMenuName.hideKeyboard()
                dismiss()
            }

            etSettingsMenuName.doOnTextChanged { text, _, _, _ ->
                viewModel.savePlayerName((text ?: "").toString())
            }

            vSettingsMenuColorRed.setOnClickListener {
                selectColor(redColor)
                redColor?.let { color -> viewModel.savePlayerColor(color) }
            }
            vSettingsMenuColorOrange.setOnClickListener {
                selectColor(orangeColor)
                orangeColor?.let { color -> viewModel.savePlayerColor(color) }
            }
            vSettingsMenuColorYellow.setOnClickListener {
                selectColor(yellowColor)
                yellowColor?.let { color -> viewModel.savePlayerColor(color) }
            }
            vSettingsMenuColorGreen.setOnClickListener {
                selectColor(greenColor)
                greenColor?.let { color -> viewModel.savePlayerColor(color) }
            }
            vSettingsMenuColorBlue.setOnClickListener {
                selectColor(blueColor)
                blueColor?.let { color -> viewModel.savePlayerColor(color) }
            }
            vSettingsMenuColorPurple.setOnClickListener {
                selectColor(purpleColor)
                purpleColor?.let { color -> viewModel.savePlayerColor(color) }
            }
            vSettingsMenuColorPink.setOnClickListener {
                selectColor(pinkColor)
                pinkColor?.let { color -> viewModel.savePlayerColor(color) }
            }
            vSettingsMenuColorWhite.setOnClickListener {
                selectColor(whiteColor)
                whiteColor?.let { color -> viewModel.savePlayerColor(color) }
            }

            btSettingMenuRestorePoints.setOnClickListener {

                fun askConfirmRestorePlayerPoints() {
                    var name = (binding.etSettingsMenuName.text ?: "").toString()
                    if (name.isBlank()) {
                        name = getString(R.string.your)
                    }
                    AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle(R.string.are_you_sure)
                        .setMessage(
                            String.format(
                                getString(R.string.restart_x_points_to_y),
                                name,
                                viewModel.initialPoints
                            )
                        )
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.ok) { _, _ ->
                            viewModel.restorePlayerPoints()
                            dismiss()
                        }
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

    override fun onDismiss(dialog: DialogInterface) {
        activityViewModel.shouldShowGameInterstitialAd = true
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}