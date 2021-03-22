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

class PlayerSettingsDialogFragment : DialogFragment() {
    
    companion object {
        
        const val TAG = "PlayerSettingsDialogFragment"
        const val KEY_INITIAL_COLOR = "key_initial_color"
        const val KEY_INITIAL_NAME = "key_initial_name"
    }
    
    interface PlayerDialogListener {
        
        fun onColorChanged(color: Int)
        fun onNameChanged(name: String?)
        fun onPlayerPointsRestarted()
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
                    redColor?.let { setNameColor(it) }
                }
                orangeColor -> {
                    vColorOrange.isSelected = true
                    orangeColor?.let { setNameColor(it) }
                }
                yellowColor -> {
                    vColorYellow.isSelected = true
                    yellowColor?.let { setNameColor(it) }
                }
                greenColor -> {
                    vColorGreen.isSelected = true
                    greenColor?.let { setNameColor(it) }
                }
                blueColor -> {
                    vColorBlue.isSelected = true
                    blueColor?.let { setNameColor(it) }
                }
                purpleColor -> {
                    vColorPurple.isSelected = true
                    purpleColor?.let { setNameColor(it) }
                }
                pinkColor -> {
                    vColorPink.isSelected = true
                    pinkColor?.let { setNameColor(it) }
                }
                else -> {
                    vColorWhite.isSelected = true
                    blackColor?.let { setNameColor(it) }
                }
            }
        }
    }
    
    private fun setNameColor(it: Int) {
        binding.etName.setTextColor(it)
        binding.etName.setHintTextColor(it)
    }
    
    private fun initListeners() {
        with(binding) {
            btClose.setOnClickListener { dismiss() }
            etName.doOnTextChanged { text, _, _, _ -> playerDialogListener?.onNameChanged((text ?: "").toString()) }
            vColorRed.setOnClickListener {
                selectColor(redColor)
                redColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorOrange.setOnClickListener {
                selectColor(orangeColor)
                orangeColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorYellow.setOnClickListener {
                selectColor(yellowColor)
                yellowColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorGreen.setOnClickListener {
                selectColor(greenColor)
                greenColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorBlue.setOnClickListener {
                selectColor(blueColor)
                blueColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorPurple.setOnClickListener {
                selectColor(purpleColor)
                purpleColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorPink.setOnClickListener {
                selectColor(pinkColor)
                pinkColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorWhite.setOnClickListener {
                selectColor(whiteColor)
                whiteColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            btResetPoints.setOnClickListener { playerDialogListener?.onPlayerPointsRestarted() }
        }
    }
    
    override fun onResume() {
        super.onResume()
        if (!isAdded) dismiss()
    }
    
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(null)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}