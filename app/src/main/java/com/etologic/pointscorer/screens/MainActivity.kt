package com.etologic.pointscorer.screens

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.etologic.pointscorer.R
import com.etologic.pointscorer.databinding.AMainActivityBinding
import com.etologic.pointscorer.screens.players_activities.*
import com.etologic.pointscorer.utils.SharedPrefsHelper
import com.etologic.pointscorer.utils.ViewExtensions.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    
    companion object {
        
        const val REP_DELAY = 100
    }
    
    private var _binding: AMainActivityBinding? = null
    private val binding get() = _binding!!
    private var errorInitialPointsLiteral: String? = null
    private var sharedPrefsHelper: SharedPrefsHelper? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initValues()
        initSharedPrefs()
        initInitialPoints()
        initListeners()
    }
    
    private fun initViewBinding() {
        _binding = AMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    private fun initValues() {
        errorInitialPointsLiteral = getString(R.string.error_initial_points)
    }
    
    private fun initSharedPrefs() {
        Thread {
            sharedPrefsHelper = SharedPrefsHelper(applicationContext)
            sharedPrefsHelper?.initRecordsIfProceed()
        }.run()
    }
    
    private fun initInitialPoints() {
        sharedPrefsHelper?.getInitialPoints()?.let { binding.tietMainInitialPoints.setText(it.toString()) }
    }
    
    private fun initListeners() {
        binding.tietMainInitialPoints.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    p0?.let { sharedPrefsHelper?.saveInitialPoints(Integer.valueOf(it.toString())) }
                } catch (nfe: NumberFormatException) {
                    binding.tietMainInitialPoints.error = errorInitialPointsLiteral
                }
            }
        })
        
        binding.acbMainResetAllPoints.setOnClickListener {
            AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(R.string.are_you_sure)
                .setMessage(R.string.restore_all_points)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _: DialogInterface?, _: Int ->
                    hideKeyboard(binding.tietMainInitialPoints)
                    sharedPrefsHelper?.resetAllPoints()
                    Snackbar.make(binding.tietMainInitialPoints, R.string.all_players_points_restored, Snackbar.LENGTH_LONG).show()
                }
                .create()
                .show()
        }
        
        binding.btMain1Player.setOnClickListener {
            binding.pbMain1Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, AOnePlayerActivity::class.java))
        }
        binding.btMain2Player.setOnClickListener {
            binding.pbMain2Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, BTwoPlayersActivity::class.java))
        }
        binding.btMain3Player.setOnClickListener {
            binding.pbMain3Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, CThreePlayersActivity::class.java))
        }
        binding.btMain4Player.setOnClickListener {
            binding.pbMain4Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, DFourPlayersActivity::class.java))
        }
        binding.btMain5Player.setOnClickListener {
            binding.pbMain5Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, EFivePlayersActivity::class.java))
        }
        binding.btMain6Player.setOnClickListener {
            binding.pbMain6Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, FSixPlayersActivity::class.java))
        }
        binding.btMain7Player.setOnClickListener {
            binding.pbMain7Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, GSevenPlayersActivity::class.java))
        }
        binding.btMain8Player.setOnClickListener {
            binding.pbMain8Player.visibility = VISIBLE
            startActivity(Intent(applicationContext, HEightPlayersActivity::class.java))
        }
    }
    
    override fun onPause() {
        binding.pbMain1Player.visibility = GONE
        binding.pbMain2Player.visibility = GONE
        binding.pbMain3Player.visibility = GONE
        binding.pbMain4Player.visibility = GONE
        binding.pbMain5Player.visibility = GONE
        binding.pbMain6Player.visibility = GONE
        binding.pbMain7Player.visibility = GONE
        binding.pbMain8Player.visibility = GONE
        hideKeyboard(binding.tietMainInitialPoints)
        super.onPause()
    }
}