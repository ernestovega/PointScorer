package com.etologic.pointscorer.screens

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.etologic.pointscorer.R.color
import com.etologic.pointscorer.databinding.PlayerSettingsDialogFragmentBinding

class PlayerSettingsDialogFragment : DialogFragment() {
    
    companion object {
        
        const val TAG = "PlayerDialogFragment"
        const val KEY_INITIAL_COLOR = "key_initial_color"
        const val KEY_INITIAL_NAME = "key_initial_name"
    }
    
    interface PlayerDialogListener {
        
        fun onColorChanged(color: Int)
        fun onNameChanged(name: String?)
    }
    
    private var _binding: PlayerSettingsDialogFragmentBinding? = null
    private val binding get() = _binding!! //This property is only valid between onCreateView and onDestroyView.
    private var initialColor: Int? = null
    private var defaultTextColor: Int? = null
    private var defaultName: String? = null
    private var playerDialogListener: PlayerDialogListener? = null
    private var initialName: String? = null
    
    internal fun setPlayerDialogListener(playerDialogListener: PlayerDialogListener?) {
        this.playerDialogListener = playerDialogListener
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PlayerSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defaultTextColor = ContextCompat.getColor(view.context, color.gray_text)
        initValues()
        initName()
        initListeners()
    }
    
    private fun initValues() {
        arguments?.let { arguments ->
            
            defaultTextColor = activity?.let { ContextCompat.getColor(it, color.gray_text) }
            
            initialColor = defaultTextColor?.let { arguments.getInt(KEY_INITIAL_COLOR, it) }
            
            val argName = arguments.getString(KEY_INITIAL_NAME, defaultName)
            initialName = if (argName.isNullOrBlank()) defaultName else argName
        }
    }
    
    private fun setColorListener(color: Int) {
        binding.tietName.setTextColor(color)
        if (playerDialogListener != null) {
            playerDialogListener!!.onColorChanged(color)
        }
    }
    
    private fun initName() {
        binding.tietName.setText(initialName)
        initialColor?.let { binding.tietName.setTextColor(it) }
    }
    
    private fun initListeners() {
        binding.btOk.setOnClickListener { dismiss() }
        binding.btCancel.setOnClickListener {
            initialColor?.let { it1 -> playerDialogListener?.onColorChanged(it1) }
            playerDialogListener?.onNameChanged(initialName)
            dismiss()
        }
    }
    
    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog!!.window != null) dialog!!.window!!.setBackgroundDrawableResource(color.colorPrimaryDark)
    }
    
    override fun onResume() {
        super.onResume()
        if (!isAdded) dismiss()
    }
    
    override fun onDismiss(dialog: DialogInterface) {
        playerDialogListener?.onNameChanged((binding.tietName.text ?: "").toString())
        super.onDismiss(dialog)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}