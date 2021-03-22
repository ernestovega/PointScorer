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
        initColor()
        initListeners()
    }
    
    private fun initValues() {
        defaultTextColor = ContextCompat.getColor(requireContext(), R.color.gray_text)
        redColor = ContextCompat.getColor(requireContext(), R.color.red)
        orangeColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        yellowColor = ContextCompat.getColor(requireContext(), R.color.yellow)
        greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        blueColor = ContextCompat.getColor(requireContext(), R.color.blue)
        purpleColor = ContextCompat.getColor(requireContext(), R.color.purple)
        pinkColor = ContextCompat.getColor(requireContext(), R.color.pink)
        whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
        arguments?.let { arguments ->
            initialColor = defaultTextColor?.let { arguments.getInt(KEY_INITIAL_COLOR, it) }
            initialName = arguments.getString(KEY_INITIAL_NAME, getString(R.string.player_name))
        }
    }
    
    private fun initName() {
        binding.etName.setText(initialName)
        initialColor?.let { binding.etName.setTextColor(it) }
    }
    
    private fun initColor() {
        selectOneColor(initialColor)
    }
    
    private fun selectOneColor(selectedColor: Int?) {
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
                redColor -> vColorRed.isSelected = true
                orangeColor -> vColorOrange.isSelected = true
                yellowColor -> vColorYellow.isSelected = true
                greenColor -> vColorGreen.isSelected = true
                blueColor -> vColorBlue.isSelected = true
                purpleColor -> vColorPurple.isSelected = true
                pinkColor -> vColorPink.isSelected = true
                //whiteColor,
                else -> vColorWhite.isSelected = true
            }
        }
    }
    
    private fun initListeners() {
        with(binding) {
            etName.doOnTextChanged { text, _, _, _ -> playerDialogListener?.onNameChanged((text ?: "").toString()) }
            vColorRed.setOnClickListener {
                selectOneColor(redColor)
                redColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorOrange.setOnClickListener {
                selectOneColor(orangeColor)
                orangeColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorYellow.setOnClickListener {
                selectOneColor(yellowColor)
                yellowColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorGreen.setOnClickListener {
                selectOneColor(greenColor)
                greenColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorBlue.setOnClickListener {
                selectOneColor(blueColor)
                blueColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorPurple.setOnClickListener {
                selectOneColor(purpleColor)
                purpleColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorPink.setOnClickListener {
                selectOneColor(pinkColor)
                pinkColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            vColorWhite.setOnClickListener {
                selectOneColor(whiteColor)
                whiteColor?.let { playerDialogListener?.onColorChanged(it) }
            }
            btOk.setOnClickListener { dismiss() }
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