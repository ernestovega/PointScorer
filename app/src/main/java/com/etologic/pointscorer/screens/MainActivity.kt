package com.etologic.pointscorer.screens

import android.R.string
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import com.etologic.pointscorer.R
import com.etologic.pointscorer.R.*
import com.etologic.pointscorer.screens.players_activities.*
import com.etologic.pointscorer.utils.MyKeyboardUtils
import com.etologic.pointscorer.utils.SharedPrefsHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.a_main_activity.*

class MainActivity : AppCompatActivity() {
    
    private var sharedPrefsHelper: SharedPrefsHelper? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.a_main_activity)
        initSharedPrefs()
        initInitialPoints()
        initListeners()
    }
    
    private fun initSharedPrefs() {
        Thread {
            sharedPrefsHelper = SharedPrefsHelper(this@MainActivity)
            sharedPrefsHelper?.initRecordsIfProceed()
        }.run()
    }
    
    private fun initInitialPoints() {
        tietMainInitialPoints?.setText(sharedPrefsHelper!!.getInitialPoints().toString())
    }
    
    private fun initListeners() {
        tietMainInitialPoints?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    sharedPrefsHelper?.saveInitialPoints(Integer.valueOf(p0.toString()))
                } catch (nfe: NumberFormatException) {
                    tietMainInitialPoints?.error = getString(R.string.error_initial_points)
                }
            }
        })
        
        acbMainResetAllPoints?.setOnClickListener {
            Builder(this, style.Theme_AppCompat_Light_Dialog)
                .setTitle(R.string.initial_points)
                .setMessage(R.string.restart_all_saved_points_)
                .setNegativeButton(string.no, null)
                .setPositiveButton(string.yes) { _: DialogInterface?, _: Int ->
                    MyKeyboardUtils.hideKeyboard(tietMainInitialPoints)
                    sharedPrefsHelper?.resetAll()
                    Snackbar.make(tietMainInitialPoints!!, R.string.all_points_restarted, Snackbar.LENGTH_LONG).show()
                }
                .create()
                .show()
        }
        
        btOnePlayer?.setOnClickListener { startActivity(Intent(this, AOnePlayerActivity::class.java)) }
        btTwoPlayers?.setOnClickListener { startActivity(Intent(this, BTwoPlayersActivity::class.java)) }
        btThreePlayers?.setOnClickListener { startActivity(Intent(this, CThreePlayersActivity::class.java)) }
        btFourPlayers?.setOnClickListener { startActivity(Intent(this, DFourPlayersActivity::class.java)) }
        btFivePlayers?.setOnClickListener { startActivity(Intent(this, EFivePlayersActivity::class.java)) }
        btSixPlayers?.setOnClickListener { startActivity(Intent(this, FSixPlayersActivity::class.java)) }
        btSevenPlayers?.setOnClickListener { startActivity(Intent(this, GSevenPlayersActivity::class.java)) }
        btEightPlayers?.setOnClickListener { startActivity(Intent(this, HEightPlayersActivity::class.java)) }
    }
    
    companion object {
        
        const val REP_DELAY = 100
    }
}