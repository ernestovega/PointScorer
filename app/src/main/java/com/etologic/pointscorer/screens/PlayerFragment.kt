package com.etologic.pointscorer.screens

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent.*
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.etologic.pointscorer.R
import com.etologic.pointscorer.databinding.PlayerFragmentBinding
import com.etologic.pointscorer.screens.PlayerSettingsDialogFragment.PlayerDialogListener
import com.etologic.pointscorer.utils.MyAnimationUtils
import com.etologic.pointscorer.utils.MyAnimationUtils.AnimationEndListener
import com.etologic.pointscorer.utils.MyConversionUtils
import com.etologic.pointscorer.utils.SharedPrefsHelper
import java.util.*
import java.util.Locale.ENGLISH

class PlayerFragment : Fragment() {
    
    companion object {
        
        private const val MAX_POINTS = 9999
        private const val MIN_POINTS = -999
        const val KEY_PLAYER_ID = "key_playerId"
        const val KEY_PLAYER_NAME_SIZE = "key_player_name_size"
        const val KEY_PLAYER_NAME_MARGIN_TOP = "key_player_name_margin_top"
        const val KEY_PLAYER_POINTS_SIZE = "key_player_points_size"
    }
    
    //FIELDS
    private lateinit var fragmentBinding: PlayerFragmentBinding
    private var sharedPrefsHelper: SharedPrefsHelper? = null
    private var playerId = 0
    private var initialPoints = 0
    private var points = 0
    private val repeatUpdateHandler = Handler()
    private var defaultTextColor: Int? = null
    private var playerNameSize = 0
    private var playerNameMarginTop = 0
    private var playerPointsSize = 0
    private var playerPointsMarginTop = 0
    private var isAutoIncrement = false /* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private var isAutoDecrement = false
    private var downCount = 0
    private var upCount = 0
    
    //LIFECYCLE
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = PlayerFragmentBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSharedPrefs()
        initValues()
        if (playerId > 0) {
            initName()
            initPoints()
            initColors()
            initShieldAndPoints()
        }
        initListeners()
    }
    
    private fun initValues() {
        defaultTextColor = activity?.let { ContextCompat.getColor(it, R.color.gray_text) }
        arguments?.let { arguments ->
            playerId = arguments.getInt(KEY_PLAYER_ID)
            playerNameSize = arguments.getInt(KEY_PLAYER_NAME_SIZE)
            playerNameMarginTop = arguments.getInt(KEY_PLAYER_NAME_MARGIN_TOP, 8)
            playerPointsSize = arguments.getInt(KEY_PLAYER_POINTS_SIZE)
            playerPointsMarginTop = 0
        } ?: run {
            playerId = 0
            playerNameSize = 16
            playerPointsSize = 48
        }
    }
    
    private fun initSharedPrefs() {
        Thread {
            context?.let { sharedPrefsHelper = SharedPrefsHelper(it) }
        }.run()
    }
    
    private fun initPoints() {
        sharedPrefsHelper?.let {
            initialPoints = it.initialPoints
            points = it.getPlayerPoints(playerId)
            with(fragmentBinding) {
                tvUpCount.textSize = playerPointsSize * 0.5f
                tvDownCount.textSize = playerPointsSize * 0.5f
                tvPointsPlayer.textSize = playerPointsSize.toFloat()
                tvPointsForAnimation.textSize = playerPointsSize.toFloat()
                context?.let { context ->
                    tvPointsPlayer.setPadding(0, MyConversionUtils.dpToPx(context, playerPointsMarginTop), 0, 0)
                    tvPointsForAnimation.setPadding(0, MyConversionUtils.dpToPx(context, playerPointsMarginTop), 0, 0)
                }
            }
        }
    }
    
    private fun initName() {
        with(fragmentBinding) {
            context?.let { etName.setPadding(0, MyConversionUtils.dpToPx(it, playerNameMarginTop), 0, 0) }
            etName.textSize = playerNameSize.toFloat()
            etName.setText(sharedPrefsHelper?.getPlayerName(playerId))
        }
    }
    
    private fun initColors() {
        sharedPrefsHelper?.let { setTextsColor(it.getPlayerColor(playerId)) }
    }
    
    private fun setTextsColor(color: Int) {
        with(fragmentBinding) {
            etName.setTextColor(color)
            etName.setHintTextColor(color)
            tvPointsPlayer.setTextColor(color)
            tvPointsForAnimation.setTextColor(color)
        }
    }
    
    private fun initShieldAndPoints() {
        with(fragmentBinding) {
            ivShield.startAnimation(MyAnimationUtils.getShieldAnimation())
            tvPointsForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsPlayer, tvPointsForAnimation, points))
        }
    }
    
    private fun initListeners() {
        with(fragmentBinding) {
            btUp.setOnLongClickListener {
                isAutoIncrement = true
                upCount = 0
                repeatUpdateHandler.post(RepeatUpdater())
            }
            btDown.setOnLongClickListener {
                isAutoDecrement = true
                downCount = 0
                repeatUpdateHandler.post(RepeatUpdater())
            }
            
            btUp.setOnTouchListener { _, motionEvent ->
                if (isAutoIncrement && (motionEvent.action == ACTION_UP || motionEvent.action == ACTION_CANCEL))
                    isAutoIncrement = false
                false
            }
            
            btDown.setOnTouchListener { _, motionEvent ->
                if (isAutoDecrement && (motionEvent.action == ACTION_UP || motionEvent.action == ACTION_CANCEL))
                    isAutoDecrement = false
                false
            }
            
            btUp.setOnClickListener {
                incrementPoints()
                updatePoints()
                tvUpCount.startAnimation(MyAnimationUtils.getFadeOutAnimation {
                    tvUpCount.text = ""
                    upCount = 0
                })
            }
            
            btDown.setOnClickListener {
                decrementPoints()
                updatePoints()
                tvDownCount.startAnimation(MyAnimationUtils.getFadeOutAnimation {
                    tvDownCount.text = ""
                    downCount = 0
                })
            }
            
            ibMenu.setOnClickListener { view ->
                context?.let { context ->
                    val popup = PopupMenu(context, view)
                    popup.setOnMenuItemClickListener { item: MenuItem ->
                        when (item.itemId) {
                            R.id.menu_edit_player -> showPlayerDialog()
                            R.id.menu_restart -> restartPlayerPoints()
                            else -> return@setOnMenuItemClickListener false
                        }
                        true
                    }
                    popup.inflate(R.menu.one_player_menu)
                    popup.show()
                }
            }
        }
    }
    
    private fun incrementPoints() {
        if (points < MAX_POINTS) {
            points++
            upCount++
        }
    }
    
    private fun decrementPoints() {
        if (points > MIN_POINTS) {
            points--
            downCount--
        }
    }
    
    private fun updatePoints() {
        var formattedUpCount = ""
        var formattedDownCount = ""
        
        if (upCount != 0) formattedUpCount = String.format(ENGLISH, "%+d", upCount)
        if (downCount != 0) formattedDownCount = String.format(ENGLISH, "%+d", downCount)
        
        fragmentBinding.tvUpCount.text = formattedUpCount
        fragmentBinding.tvDownCount.text = formattedDownCount
        val animationEndListener = AnimationEndListener {
            Thread { sharedPrefsHelper?.savePlayerPoints(points, playerId) }.run()
        }
        val updatePointsAnimation = MyAnimationUtils.getUpdatePointsAnimation(
            fragmentBinding.tvPointsPlayer,
            fragmentBinding.tvPointsForAnimation,
            points,
            animationEndListener
        )
        fragmentBinding.tvPointsForAnimation.startAnimation(updatePointsAnimation)
    }
    
    private fun showPlayerDialog() {
        if (activity != null) {
            val playerDialogFragment = PlayerSettingsDialogFragment()
            val bundle = Bundle()
            
            bundle.putInt(PlayerSettingsDialogFragment.KEY_INITIAL_COLOR, fragmentBinding.etName.currentTextColor)
            val name =
                if (fragmentBinding.etName.text == null)
                    sharedPrefsHelper?.getPlayerName(playerId)
                else
                    fragmentBinding.etName.text.toString()
            bundle.putString(PlayerSettingsDialogFragment.KEY_INITIAL_NAME, name)
            
            playerDialogFragment.arguments = bundle
            playerDialogFragment.show(activity!!.supportFragmentManager, PlayerSettingsDialogFragment.TAG)
            playerDialogFragment.setPlayerDialogListener(object : PlayerDialogListener {
                override fun onColorChanged(color: Int) {
                    sharedPrefsHelper?.savePlayerColor(color, playerId)
                    (if (color == 0) defaultTextColor else color)?.let { setTextsColor(it) }
                }
    
                override fun onNameChanged(name: String?) {
                    sharedPrefsHelper?.savePlayerName(name, playerId)
                    fragmentBinding.etName.setText(name)
                }
            })
        }
    }
    
    private fun restartPlayerPoints() {
        points = initialPoints
        updatePoints()
    }
    
    //INNER CLASSES
    internal inner class RepeatUpdater : Runnable {
        
        override fun run() {
            when {
                isAutoIncrement -> incrementPoints()
                isAutoDecrement -> decrementPoints()
                else -> {
                    with(fragmentBinding) {
                        tvUpCount.startAnimation(MyAnimationUtils.getFadeOutAnimation {
                            tvUpCount.text = ""
                            upCount = 0
                        })
                        tvDownCount.startAnimation(MyAnimationUtils.getFadeOutAnimation {
                            tvDownCount.text = ""
                            downCount = 0
                        })
                    }
                    return
                }
            }
            sharedPrefsHelper?.savePlayerPoints(points, playerId)
            updatePoints()
            repeatUpdateHandler.postDelayed(RepeatUpdater(), MainActivity.REP_DELAY.toLong())
        }
    }
}