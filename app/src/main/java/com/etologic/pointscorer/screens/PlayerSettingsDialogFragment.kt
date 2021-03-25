package com.etologic.pointscorer.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.etologic.pointscorer.R
import com.etologic.pointscorer.databinding.PlayerSettingsDialogFragmentBinding
import com.etologic.pointscorer.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.utils.ViewExtensions.showKeyboard

class PlayerSettingsDialogFragment : DialogFragment() {
    
    companion object {
        
        const val TAG = "PlayerSettingsDialogFragment"
        const val KEY_INITIAL_COLOR = "key_initial_color"
        const val KEY_INITIAL_NAME = "key_initial_name"
    }
    
    interface PlayerDialogListener {
        
        fun onColorChanged(color: Int)
        fun onNameChanged(name: String?)
    }
    
    private var _binding: PlayerSettingsDialogFragmentBinding? = null
    private val binding get() = _binding!! //This property is only valid between onCreateView and onDestroyView.
    private var defaultTextColor: Int? = null
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
        _binding = PlayerSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initName()
        selectColor(initialColor)
        initListeners()
        binding.etName.requestFocus()
        binding.etName.showKeyboard()
    }
    
    private fun initValues() {
        defaultTextColor = ContextCompat.getColor(requireContext(), R.color.gray_text)
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
            initialColor = defaultTextColor?.let { arguments.getInt(KEY_INITIAL_COLOR, it) }
            initialName = arguments.getString(KEY_INITIAL_NAME, getString(R.string.player_name))
        }
    }
    
    private fun initName() {
        binding.etName.setText(initialName)
        initialColor?.let { binding.etName.setTextColor(it) }
    }
    
    private fun selectColor(selectedColor: Int?) {
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
    
    private fun setNameColor(textColor: Int?, hintColor: Int?) {
        textColor?.let { binding.etName.setTextColor(it) }
        hintColor?.let { binding.etName.setHintTextColor(it) }
    }
    
    private fun initListeners() {
        with(binding) {
            etName.doOnTextChanged { text, _, _, _ -> playerDialogListener?.onNameChanged((text ?: "").toString()) }
            
            vColorRed.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(redColor)
                redColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorOrange.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(orangeColor)
                orangeColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorYellow.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(yellowColor)
                yellowColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorGreen.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(greenColor)
                greenColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorBlue.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(blueColor)
                blueColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorPurple.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(purpleColor)
                purpleColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorPink.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(pinkColor)
                pinkColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorWhite.setOnClickListener {
                hideKeyboard(binding.etName)
                selectColor(whiteColor)
                whiteColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            
            btOk.setOnClickListener {
                hideKeyboard(binding.etName)
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