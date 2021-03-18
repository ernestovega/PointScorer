package com.etologic.pointscorer.screens

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.etologic.pointscorer.R.color
import com.etologic.pointscorer.databinding.PlayerDialogFragmentBinding

class PlayerDialogFragment : DialogFragment() {
    
    companion object {
        
        const val TAG = "PlayerDialogFragment"
        const val KEY_INITIAL_COLOR = "key_initial_color"
        const val KEY_INITIAL_NAME = "key_initial_name"
    }
    
    interface PlayerDialogListener {
        
        fun onColorChanged(color: Int)
        fun onNameChanged(name: String?)
    }
    
    private lateinit var fragmentBinding: PlayerDialogFragmentBinding
    private var initialColor: Int? = null
    private var defaultTextColor: Int? = null
    private var defaultName: String? = null
    private var playerDialogListener: PlayerDialogListener? = null
    private var initialName: String? = null
    
    internal fun setPlayerDialogListener(playerDialogListener: PlayerDialogListener?) {
        this.playerDialogListener = playerDialogListener
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = PlayerDialogFragmentBinding.inflate(inflater, container, false)
        return fragmentBinding.root
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
        fragmentBinding.tietName.setTextColor(color)
        if (playerDialogListener != null) {
            playerDialogListener!!.onColorChanged(color)
        }
    }
    
    private fun initName() {
        fragmentBinding.tietName.setText(initialName)
        initialColor?.let { fragmentBinding.tietName.setTextColor(it) }
    }
    
    private fun initListeners() {
        fragmentBinding.btOk.setOnClickListener { dismiss() }
        fragmentBinding.btCancel.setOnClickListener {
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
        playerDialogListener?.onNameChanged((fragmentBinding.tietName.text ?: "").toString())
        super.onDismiss(dialog)
    }
}