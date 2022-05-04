package com.etologic.pointscorer.app.main.fragments.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.databinding.GamePlayerSettingsDialogFragmentBinding

class PlayerSettingsDialogFragment : DialogFragment() {

    companion object {

        const val TAG = "PlayerSettingsDialogFragment"
        const val KEY_INITIAL_COLOR = "key_initial_color"
        const val KEY_INITIAL_NAME = "key_initial_name"
    }

    interface PlayerDialogListener {
        fun onColorChanged(color: Int)
        fun onNameChanged(name: String)
    }

    private var _binding: GamePlayerSettingsDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private var initialColor: Int? = null
    private var initialName: String? = null
    private var playerDialogListener: PlayerDialogListener? = null
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

    internal fun setPlayerDialogListener(playerDialogListener: PlayerDialogListener?) {
        this.playerDialogListener = playerDialogListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GamePlayerSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initName()
        selectColor(initialColor)
        initListeners()
        binding.etName.requestFocus()
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
        redTransparentColor = ContextCompat.getColor(requireContext(), R.color.red)
        orangeTransparentColor = ContextCompat.getColor(requireContext(), R.color.orange)
        yellowTransparentColor = ContextCompat.getColor(requireContext(), R.color.yellow)
        greenTransparentColor = ContextCompat.getColor(requireContext(), R.color.green)
        blueTransparentColor = ContextCompat.getColor(requireContext(), R.color.blue)
        purpleTransparentColor = ContextCompat.getColor(requireContext(), R.color.purple)
        pinkTransparentColor = ContextCompat.getColor(requireContext(), R.color.pink)
        blackTransparentColor = ContextCompat.getColor(requireContext(), R.color.black)
        arguments?.let { arguments ->
            initialColor = whiteColor?.let { arguments.getInt(KEY_INITIAL_COLOR, it) }
            initialName = arguments.getString(KEY_INITIAL_NAME, getString(R.string.player_name))
        }
    }

    private fun initName() {
        binding.etName.setText(initialName)
        initialColor?.let { binding.etName.setTextColor(it) }
    }

    private fun selectColor(selectedColor: Int?) {

        fun setNameColor(textColor: Int?, hintColor: Int?) {
            textColor?.let { binding.etName.setTextColor(it) }
            hintColor?.let { binding.etName.setHintTextColor(it) }
        }

        with(binding) {
            vColorRed.isSelected = false
            vColorOrange.isSelected = false
            vColorYellow.isSelected = false
            vColorGreen.isSelected = false
            vColorBlue.isSelected = false
            vColorPurple.isSelected = false
            vColorPink.isSelected = false
            vColorWhite.isSelected = false
            when (selectedColor) {
                redColor -> {
                    vColorRed.isSelected = true
                    setNameColor(redColor, redTransparentColor)
                }
                orangeColor -> {
                    vColorOrange.isSelected = true
                    setNameColor(orangeColor, orangeTransparentColor)
                }
                yellowColor -> {
                    vColorYellow.isSelected = true
                    setNameColor(yellowColor, yellowTransparentColor)
                }
                greenColor -> {
                    vColorGreen.isSelected = true
                    setNameColor(greenColor, greenTransparentColor)
                }
                blueColor -> {
                    vColorBlue.isSelected = true
                    setNameColor(blueColor, blueTransparentColor)
                }
                purpleColor -> {
                    vColorPurple.isSelected = true
                    setNameColor(purpleColor, purpleTransparentColor)
                }
                pinkColor -> {
                    vColorPink.isSelected = true
                    setNameColor(pinkColor, pinkTransparentColor)
                }
                else -> {
                    vColorWhite.isSelected = true
                    setNameColor(blackColor, blackTransparentColor)
                }
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            etName.doOnTextChanged { text, _, _, _ -> playerDialogListener?.onNameChanged((text ?: "").toString()) }

            vColorRed.setOnClickListener {
                selectColor(redColor)
                redColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }
            vColorOrange.setOnClickListener {
                selectColor(orangeColor)
                orangeColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }
            vColorYellow.setOnClickListener {
                selectColor(yellowColor)
                yellowColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }
            vColorGreen.setOnClickListener {
                selectColor(greenColor)
                greenColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }
            vColorBlue.setOnClickListener {
                selectColor(blueColor)
                blueColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }
            vColorPurple.setOnClickListener {
                selectColor(purpleColor)
                purpleColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }
            vColorPink.setOnClickListener {
                selectColor(pinkColor)
                pinkColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }
            vColorWhite.setOnClickListener {
                selectColor(whiteColor)
                whiteColor?.let { color -> playerDialogListener?.onColorChanged(color) }
            }

            btOk.setOnClickListener {
                etName.hideKeyboard()
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(null)
        dialog?.setCancelable(false)
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