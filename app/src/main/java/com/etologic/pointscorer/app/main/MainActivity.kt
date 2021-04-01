package com.etologic.pointscorer.app.main

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.game_fragments.*
import com.etologic.pointscorer.databinding.MainActivityBinding
import com.etologic.pointscorer.utils.SharedPrefsHelper
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    
    @Inject
    internal lateinit var viewModelFactory: MainActivityViewModelFactory
    private lateinit var viewModel: MainActivityViewModel
    private var _activityBinding: MainActivityBinding? = null
    private val binding get() = _activityBinding!!
    private var errorInitialPointsLiteral: String? = null
    private var sharedPrefsHelper: SharedPrefsHelper? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
    }
    
    private fun initViewBinding() {
        _activityBinding = MainActivityBinding.inflate(layoutInflater)
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
                    binding.tietMainInitialPoints.hideKeyboard()
                    sharedPrefsHelper?.resetAllPoints()
                    Snackbar.make(binding.tietMainInitialPoints, R.string.all_players_points_restored, Snackbar.LENGTH_LONG).show()
                }
                .create()
                .show()
        }
        
        binding.btMain1Player.setOnClickListener {
            binding.pbMain1Player.visibility = View.VISIBLE
            goToFragment(BOnePlayerFragment())
        }
        binding.btMain2Player.setOnClickListener {
            binding.pbMain2Player.visibility = View.VISIBLE
            goToFragment(CTwoPlayersFragment())
        }
        binding.btMain3Player.setOnClickListener {
            binding.pbMain3Player.visibility = View.VISIBLE
            goToFragment(DThreePlayersFragment())
        }
        binding.btMain4Player.setOnClickListener {
            binding.pbMain4Player.visibility = View.VISIBLE
            goToFragment(EFourPlayersFragment())
        }
        binding.btMain5Player.setOnClickListener {
            binding.pbMain5Player.visibility = View.VISIBLE
            goToFragment(FFivePlayersFragment())
        }
        binding.btMain6Player.setOnClickListener {
            binding.pbMain6Player.visibility = View.VISIBLE
            goToFragment(GSixPlayersFragment())
        }
        binding.btMain7Player.setOnClickListener {
            binding.pbMain7Player.visibility = View.VISIBLE
            goToFragment(HSevenPlayersFragment())
        }
        binding.btMain8Player.setOnClickListener {
            binding.pbMain8Player.visibility = View.VISIBLE
            goToFragment(IEightPlayersFragment())
        }
    }
    
    private fun goToFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        fragmentTransaction.replace(R.id.flMain, fragment)
        fragmentTransaction.commit()
    }
    
    override fun onPause() {
        binding.pbMain1Player.visibility = View.GONE
        binding.pbMain2Player.visibility = View.GONE
        binding.pbMain3Player.visibility = View.GONE
        binding.pbMain4Player.visibility = View.GONE
        binding.pbMain5Player.visibility = View.GONE
        binding.pbMain6Player.visibility = View.GONE
        binding.pbMain7Player.visibility = View.GONE
        binding.pbMain8Player.visibility = View.GONE
        binding.tietMainInitialPoints.hideKeyboard()
        super.onPause()
    }
    
    companion object {
        
        const val REP_DELAY = 100
    }
}