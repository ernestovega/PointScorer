package com.etologic.pointscorer.screens

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
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
    
    internal fun setPlayerDialogListener(playerDialogListener: PlayerDialogListener?) {
        this.playerDialogListener = playerDialogListener
    }
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = PlayerSettingsDialogFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initName()
        initListeners()
    }
    
    private fun initValues() {
        defaultTextColor = ContextCompat.getColor(requireContext(), R.color.gray_text)
        arguments?.let { arguments ->
            initialColor = defaultTextColor?.let { arguments.getInt(KEY_INITIAL_COLOR, it) }
            initialName = arguments.getString(KEY_INITIAL_NAME, getString(R.string.player_name))
        }
    }
    
    private fun initName() {
        binding.etName.setText(initialName)
        initialColor?.let { binding.etName.setTextColor(it) }
    }
    
    private fun initListeners() {
        binding.etName.doOnTextChanged { text, _, _, _ -> playerDialogListener?.onNameChanged((text ?: "").toString()) }
        binding.btOk.setOnClickListener { dismiss() }
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