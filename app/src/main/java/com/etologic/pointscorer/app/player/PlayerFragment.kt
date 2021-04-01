package com.etologic.pointscorer.app.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent.ACTION_CANCEL
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.etologic.pointscorer.R
import com.etologic.pointscorer.databinding.PlayerFragmentBinding
import com.etologic.pointscorer.app.player.PlayerSettingsDialogFragment.PlayerDialogListener
import com.etologic.pointscorer.app.main.MainActivity
import com.etologic.pointscorer.utils.MyAnimationUtils
import com.etologic.pointscorer.utils.MyAnimationUtils.AnimationEndListener
import com.etologic.pointscorer.utils.MyConversionUtils
import com.etologic.pointscorer.utils.SharedPrefsHelper
import kotlinx.android.synthetic.main.player_settings_dialog_fragment.*
import java.util.Locale.ENGLISH

class PlayerFragment : Fragment(), PlayerDialogListener {
    
    companion object {
        
        private const val MAX_POINTS = 9999
        private const val MIN_POINTS = -999
        const val KEY_PLAYER_ID = "key_playerId"
        const val KEY_PLAYER_NAME_SIZE = "key_player_name_size"
        const val KEY_PLAYER_NAME_MARGIN_TOP = "key_player_name_margin_top"
        const val KEY_PLAYER_POINTS_SIZE = "key_player_points_size"
    }
    
    //FIELDS
    private var _binding: PlayerFragmentBinding? = null
    private val binding get() = _binding!!
    private var sharedPrefsHelper: SharedPrefsHelper? = null
    private var playerId = 0
    private var initialPoints = 0
    private var points = 0
    private val repeatUpdateHandler = Handler()
    private var defaultTextColor: Int? = null
    private var playerNameSize = 16
    private var playerNameMarginTop = 0
    private var playerPointsSize = 48
    private var playerPointsMarginTop = 0
    private var isAutoIncrement = false /* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private var isAutoDecrement = false
    private var downCount = 0
    private var upCount = 0
//    private var confirmRestorePointsDialog: AlertDialog? = null
    
    //EVENTS
    override fun onColorChanged(color: Int) {
        sharedPrefsHelper?.savePlayerColor(color, playerId)
        (if (color == 0) defaultTextColor else color)?.let { setTextsColor(it) }
    }
    
    override fun onNameChanged(name: String?) {
        sharedPrefsHelper?.savePlayerName(name, playerId)
        binding.etName.setText(name)
    }
//    override fun onPlayerPointsRestarted() {
//        if (confirmRestorePointsDialog == null)
//            confirmRestorePointsDialog = getConfirmResetPlayerPointsDialog()
//        confirmRestorePointsDialog?.show()
//    }
//    private fun getConfirmResetPlayerPointsDialog() =
//        Builder(requireContext(), style.Theme_AppCompat_Light_Dialog)
//            .setTitle(R.string.are_you_sure)
//            .setMessage(String.format(getString(R.string.restart_x_points_to_y), (etName.text ?: "").toString(), initialPoints))
//            .setNegativeButton(string.no, null)
//            .setPositiveButton(string.yes) { _, _ -> resetPlayerPoints() }
//            .create()
//    private var confirmRestoreAllPointsDialog: AlertDialog? = null
//
//    override fun onAllPlayerPointsRestarted() {
//        if (confirmRestoreAllPointsDialog == null)
//            confirmRestoreAllPointsDialog = getConfirmRestoreAllPlayersPointsDialog()
//        confirmRestoreAllPointsDialog?.show()
//    }
//    private fun getConfirmRestoreAllPlayersPointsDialog() =
//        Builder(requireContext(), style.Theme_AppCompat_Light_Dialog)
//            .setTitle(R.string.are_you_sure)
//            .setMessage(String.format(getString(R.string.restart_all_points_to_y), initialPoints))
//            .setNegativeButton(string.no, null)
//            .setPositiveButton(string.yes) { _, _ -> activityViewModel.resetAllPlayersPoints() }
//            .create()
    //LIFECYCLE
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = PlayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
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
        defaultTextColor = ContextCompat.getColor(requireContext(), R.color.gray_text)
        arguments?.let { arguments ->
            playerId = arguments.getInt(KEY_PLAYER_ID)
            playerNameSize = arguments.getInt(KEY_PLAYER_NAME_SIZE)
            playerNameMarginTop = arguments.getInt(KEY_PLAYER_NAME_MARGIN_TOP, 8)
            playerPointsSize = arguments.getInt(KEY_PLAYER_POINTS_SIZE)
        }
    }
    
    private fun initSharedPrefs() {
        Thread {
            sharedPrefsHelper = SharedPrefsHelper(requireContext())
        }.run()
    }
    
    private fun initPoints() {
        sharedPrefsHelper?.let {
            initialPoints = it.initialPoints
            points = it.getPlayerPoints(playerId)
            with(binding) {
                tvUpCount.textSize = playerPointsSize * 0.5f
                tvDownCount.textSize = playerPointsSize * 0.5f
                tvPointsPlayer.textSize = playerPointsSize.toFloat()
                tvPointsForAnimation.textSize = playerPointsSize.toFloat()
                tvPointsPlayer.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
                tvPointsForAnimation.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
            }
        }
    }
    
    private fun initName() {
        with(binding) {
            etName.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerNameMarginTop), 0, 0)
            etName.textSize = playerNameSize.toFloat()
            etName.setText(sharedPrefsHelper?.getPlayerName(playerId))
        }
    }
    
    private fun initColors() {
        sharedPrefsHelper?.let { setTextsColor(it.getPlayerColor(playerId)) }
    }
    
    private fun setTextsColor(color: Int) {
        with(binding) {
            etName.setTextColor(color)
            etName.setHintTextColor(color)
            tvPointsPlayer.setTextColor(color)
            tvPointsForAnimation.setTextColor(color)
        }
    }
    
    private fun initShieldAndPoints() {
        with(binding) {
            ivShield.startAnimation(MyAnimationUtils.getShieldAnimation())
            tvPointsForAnimation.startAnimation(MyAnimationUtils.getUpdateShieldPointsAnimation(tvPointsPlayer, tvPointsForAnimation, points))
        }
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        with(binding) {
            ibMenu.setOnClickListener { view ->
                val popup = PopupMenu(requireContext(), view)
                popup.setOnMenuItemClickListener { item: MenuItem ->
                    when (item.itemId) {
                        R.id.menu_edit_player -> showPlayerDialog()
                        R.id.menu_restart_points -> restorePlayerPoints()
                        else -> return@setOnMenuItemClickListener false
                    }
                    true
                }
                popup.inflate(R.menu.player_menu)
                popup.show()
            }
            
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
                tvUpCount.startAnimation(MyAnimationUtils.getAuxPointsFadeOutAnimation {
                    tvUpCount.text = ""
                    upCount = 0
                })
            }
            
            btDown.setOnClickListener {
                decrementPoints()
                updatePoints()
                tvDownCount.startAnimation(MyAnimationUtils.getAuxPointsFadeOutAnimation {
                    tvDownCount.text = ""
                    downCount = 0
                })
            }
        }
    }
    
    private fun restorePlayerPoints() {
        points = initialPoints
        updatePoints()
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
        
        binding.tvUpCount.text = formattedUpCount
        binding.tvDownCount.text = formattedDownCount
        val animationEndListener = AnimationEndListener {
            Thread { sharedPrefsHelper?.savePlayerPoints(points, playerId) }.run()
        }
        val updatePointsAnimation = MyAnimationUtils.getUpdateShieldPointsAnimation(
            binding.tvPointsPlayer,
            binding.tvPointsForAnimation,
            points,
            animationEndListener
        )
        binding.tvPointsForAnimation.startAnimation(updatePointsAnimation)
    }
    
    private fun showPlayerDialog() {
        val playerDialogFragment = PlayerSettingsDialogFragment()
        val bundle = Bundle()
        
        bundle.putInt(PlayerSettingsDialogFragment.KEY_INITIAL_COLOR, binding.etName.currentTextColor)
        val name =
            if (binding.etName.text == null)
                sharedPrefsHelper?.getPlayerName(playerId)
            else
                binding.etName.text.toString()
        bundle.putString(PlayerSettingsDialogFragment.KEY_INITIAL_NAME, name)
        
        playerDialogFragment.arguments = bundle
        playerDialogFragment.setPlayerDialogListener(this)
        
        playerDialogFragment.show(requireActivity().supportFragmentManager, PlayerSettingsDialogFragment.TAG)
    }
    
    //INNER CLASSES
    internal inner class RepeatUpdater : Runnable {
        
        override fun run() {
            when {
                isAutoIncrement -> incrementPoints()
                isAutoDecrement -> decrementPoints()
                else -> {
                    with(binding) {
                        tvUpCount.startAnimation(MyAnimationUtils.getAuxPointsFadeOutAnimation {
                            tvUpCount.text = ""
                            upCount = 0
                        })
                        tvDownCount.startAnimation(MyAnimationUtils.getAuxPointsFadeOutAnimation {
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