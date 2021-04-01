package com.etologic.pointscorer.app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.databinding.MainFragmentBinding

class MainFragment : BaseMainFragment() {
    
    private var fragmentBinding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requireActivity().window.addFlags(FLAG_FULLSCREEN)
//        requireActivity().window.addFlags(FLAG_KEEP_SCREEN_ON)
    }
}