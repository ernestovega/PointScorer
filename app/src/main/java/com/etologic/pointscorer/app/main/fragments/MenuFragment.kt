package com.etologic.pointscorer.app.main.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.*
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.databinding.MenuFragmentBinding
import java.util.Locale.ENGLISH

class MenuFragment : BaseMainFragment() {
    
    private var fragmentBinding: MenuFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    private var errorInitialPointsLiteral: String? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = MenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initListeners()
    }
    
    private fun initValues() {
        errorInitialPointsLiteral = getString(R.string.error_initial_points)
        activityViewModel.liveInitialPoints.observe(viewLifecycleOwner) {
            binding.etMainInitialPoints?.setText(it.toString())
            activityViewModel.liveInitialPoints.removeObservers(viewLifecycleOwner)
        }
        activityViewModel.getInitialPoints()
    }
    
    private fun initListeners() {
        binding.etMainInitialPoints?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    try {
                        activityViewModel.saveInitialPoints(p0.toString().toInt())
                    } catch (nfe: NumberFormatException) {
                        binding.etMainInitialPoints?.error = errorInitialPointsLiteral
                    }
                }
            }
    
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        
        binding.acbMainResetAllPoints.setOnClickListener {
            AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(R.string.are_you_sure)
                .setMessage(String.format(ENGLISH, getString(R.string.this_will_restore_all_points), activityViewModel.liveInitialPoints.value))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                    binding.etMainInitialPoints?.hideKeyboard()
                    activityViewModel.restoreAllGamesPoints()
                    Toast.makeText(requireContext(), R.string.all_players_points_were_restored, LENGTH_LONG).show()
                }
                .create()
                .show()
        }
        
        binding.btMain1Player.setOnClickListener {
            binding.btMain1Player.visibility = GONE
            binding.pbMain1Player.visibility = VISIBLE
            activityViewModel.navigateTo(ONE_PLAYER)
        }
        binding.btMain2Player.setOnClickListener {
            binding.btMain2Player.visibility = GONE
            binding.pbMain2Player.visibility = VISIBLE
            activityViewModel.navigateTo(TWO_PLAYER)
        }
        binding.btMain3Player.setOnClickListener {
            binding.btMain3Player.visibility = GONE
            binding.pbMain3Player.visibility = VISIBLE
            activityViewModel.navigateTo(THREE_PLAYER)
        }
        binding.btMain4Player.setOnClickListener {
            binding.btMain4Player.visibility = GONE
            binding.pbMain4Player.visibility = VISIBLE
            activityViewModel.navigateTo(FOUR_PLAYER)
        }
        binding.btMain5Player.setOnClickListener {
            binding.btMain5Player.visibility = GONE
            binding.pbMain5Player.visibility = VISIBLE
            activityViewModel.navigateTo(FIVE_PLAYER)
        }
        binding.btMain6Player.setOnClickListener {
            binding.btMain6Player.visibility = GONE
            binding.pbMain6Player.visibility = VISIBLE
            activityViewModel.navigateTo(SIX_PLAYER)
        }
        binding.btMain7Player.setOnClickListener {
            binding.btMain7Player.visibility = GONE
            binding.pbMain7Player.visibility = VISIBLE
            activityViewModel.navigateTo(SEVEN_PLAYER)
        }
        binding.btMain8Player.setOnClickListener {
            binding.btMain8Player.visibility = GONE
            binding.pbMain8Player.visibility = VISIBLE
            activityViewModel.navigateTo(EIGHT_PLAYER)
        }
    }
    
    override fun onStop() {
        binding.pbMain1Player.visibility = GONE
        binding.pbMain2Player.visibility = GONE
        binding.pbMain3Player.visibility = GONE
        binding.pbMain4Player.visibility = GONE
        binding.pbMain5Player.visibility = GONE
        binding.pbMain6Player.visibility = GONE
        binding.pbMain7Player.visibility = GONE
        binding.pbMain8Player.visibility = GONE
        binding.btMain1Player.visibility = VISIBLE
        binding.btMain2Player.visibility = VISIBLE
        binding.btMain3Player.visibility = VISIBLE
        binding.btMain4Player.visibility = VISIBLE
        binding.btMain5Player.visibility = VISIBLE
        binding.btMain6Player.visibility = VISIBLE
        binding.btMain7Player.visibility = VISIBLE
        binding.btMain8Player.visibility = VISIBLE
        binding.etMainInitialPoints?.hideKeyboard()
        super.onStop()
    }
}