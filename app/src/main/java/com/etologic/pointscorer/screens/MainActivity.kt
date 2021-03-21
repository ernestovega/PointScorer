package com.etologic.pointscorer.screens

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import com.etologic.pointscorer.R
import com.etologic.pointscorer.databinding.AMainActivityBinding
import com.etologic.pointscorer.screens.players_activities.*
import com.etologic.pointscorer.utils.MyKeyboardUtils
import com.etologic.pointscorer.utils.SharedPrefsHelper
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
            Builder(this, R.style.AppTheme) //Theme_AppCompat_Light_Dialog)
                .setTitle(R.string.initial_points)
                .setMessage(R.string.restart_all_saved_points_)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _: DialogInterface?, _: Int ->
                    MyKeyboardUtils.hideKeyboard(binding.tietMainInitialPoints)
                    sharedPrefsHelper?.resetAll()
                    Snackbar.make(binding.tietMainInitialPoints, R.string.all_points_restarted, Snackbar.LENGTH_LONG).show()
                }
                .create()
                .show()
        }
        
        binding.btOnePlayer.setOnClickListener { startActivity(Intent(applicationContext, AOnePlayerActivity::class.java)) }
        binding.btTwoPlayers.setOnClickListener { startActivity(Intent(applicationContext, BTwoPlayersActivity::class.java)) }
        binding.btThreePlayers.setOnClickListener { startActivity(Intent(applicationContext, CThreePlayersActivity::class.java)) }
        binding.btFourPlayers.setOnClickListener { startActivity(Intent(applicationContext, DFourPlayersActivity::class.java)) }
        binding.btFivePlayers.setOnClickListener { startActivity(Intent(applicationContext, EFivePlayersActivity::class.java)) }
        binding.btSixPlayers.setOnClickListener { startActivity(Intent(applicationContext, FSixPlayersActivity::class.java)) }
        binding.btSevenPlayers.setOnClickListener { startActivity(Intent(applicationContext, GSevenPlayersActivity::class.java)) }
        binding.btEightPlayers.setOnClickListener { startActivity(Intent(applicationContext, HEightPlayersActivity::class.java)) }
    }
}