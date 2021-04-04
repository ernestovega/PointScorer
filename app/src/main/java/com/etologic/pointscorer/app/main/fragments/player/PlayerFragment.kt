package com.etologic.pointscorer.app.main.fragments.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent.ACTION_CANCEL
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.R.menu
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.fragments.Game1PlayerFragment.Companion.GAME_1_PLAYER_1_ID
import com.etologic.pointscorer.app.main.fragments.player.PlayerSettingsDialogFragment.PlayerDialogListener
import com.etologic.pointscorer.app.utils.MyAnimationUtils
import com.etologic.pointscorer.app.utils.MyAnimationUtils.AnimationEndListener
import com.etologic.pointscorer.app.utils.MyConversionUtils
import com.etologic.pointscorer.databinding.GamePlayerFragmentBinding
import java.util.Locale.ENGLISH

class PlayerFragment : BaseMainFragment(), PlayerDialogListener {
    
    companion object {
        
        private const val MAX_POINTS = 9999
        private const val MIN_POINTS = -999
        const val KEY_PLAYER_ID = "key_player_id"
        const val KEY_PLAYER_NAME_SIZE = "key_player_name_size"
        const val KEY_PLAYER_NAME_MARGIN_TOP = "key_player_name_margin_top"
        const val KEY_PLAYER_POINTS_SIZE = "key_player_points_size"
        const val REP_DELAY = 100
        private const val RESTORE_ALL_POINTS_ITEM_INDEX = 2
    }
    
    //FIELDS
    private var _binding: GamePlayerFragmentBinding? = null
    private val binding get() = _binding!!
    private var playerId = 0
    private var initialPoints = 0
    private var points = 0
    private val repeatUpdateHandler = Handler()
    private var defaultPlayerColor: Int? = null
    private var playerNameSize = 16
    private var playerNameMarginTop = 0
    private var playerPointsSize = 48
    private var playerPointsMarginTop = 0
    private var isAutoIncrement = false /* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private var isAutoDecrement = false
    private var downCount = 0
    private var upCount = 0
    private var popup: PopupMenu? = null
    private var numberOfPlayersInThisGame: Int = 0
        get() {
            if (field == 0)
                field = playerId / 10
            return field
        }
    
    //EVENTS
    override fun onColorChanged(color: Int) {
        activityViewModel.savePlayerColor(color, playerId)
        (if (color == 0) defaultPlayerColor else color)?.let { setTextsColor(it) }
    }
    
    override fun onNameChanged(name: String) {
        activityViewModel.savePlayerName(playerId, name)
        binding.etName.setText(name)
    }
    
    //LIFECYCLE
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GamePlayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        if (playerId > 0) {
            initName()
            initPoints()
            initColors()
            initShieldAndPoints()
        }
        initObservers()
        initListeners()
    }
    
    private fun initValues() {
        defaultPlayerColor = ContextCompat.getColor(requireContext(), R.color.white)
        arguments?.let { arguments ->
            playerId = arguments.getInt(KEY_PLAYER_ID)
            playerNameSize = arguments.getInt(KEY_PLAYER_NAME_SIZE)
            playerNameMarginTop = arguments.getInt(KEY_PLAYER_NAME_MARGIN_TOP, 8)
            playerPointsSize = arguments.getInt(KEY_PLAYER_POINTS_SIZE)
        }
        initialPoints = activityViewModel.getInitialPoints()
    }
    
    private fun initPoints() {
        points = activityViewModel.getPlayerPoints(playerId)
        with(binding) {
            tvUpCount.textSize = playerPointsSize * 0.5f
            tvDownCount.textSize = playerPointsSize * 0.5f
            tvPointsPlayer.textSize = playerPointsSize.toFloat()
            tvPointsForAnimation.textSize = playerPointsSize.toFloat()
            tvPointsPlayer.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
            tvPointsForAnimation.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
        }
    }
    
    private fun initName() {
        with(binding) {
            etName.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerNameMarginTop), 0, 0)
            etName.textSize = playerNameSize.toFloat()
            etName.setText(activityViewModel.getPlayerName(playerId))
        }
    }
    
    private fun initColors() {
        setTextsColor(activityViewModel.getPlayerColor(playerId))
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
    
    private fun initObservers() {
        activityViewModel.getShouldRestoreAllPoints().observe(viewLifecycleOwner, { restorePlayerPointsIfProceed(it) })
    }
    
    private fun restorePlayerPointsIfProceed(numberOfPlayersInTheGameToRestore: Int) {
        if (numberOfPlayersInTheGameToRestore == numberOfPlayersInThisGame)
            restorePlayerPoints()
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        with(binding) {
            ibMenu.setOnClickListener {
                if (popup == null)
                    buildPopupMenu()
                popup!!.show()
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
                if (isAutoIncrement &&
                    (motionEvent.action == ACTION_UP || motionEvent.action == ACTION_CANCEL)
                )
                    isAutoIncrement = false
                false
            }
            
            btDown.setOnTouchListener { _, motionEvent ->
                if (isAutoDecrement &&
                    (motionEvent.action == ACTION_UP || motionEvent.action == ACTION_CANCEL)
                )
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
    
    private fun buildPopupMenu() {
        popup = PopupMenu(requireActivity(), binding.ibMenu)
        popup!!.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_edit_player -> showPlayerDialog()
                R.id.menu_restart_points -> askConfirmRestorePlayerPoints()
                R.id.menu_restart_game_points -> askConfirmRestoreGamePlayersPoints()
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
        popup!!.inflate(menu.game_player_menu)
        if (playerId == GAME_1_PLAYER_1_ID)
            popup!!.menu.getItem(RESTORE_ALL_POINTS_ITEM_INDEX)?.isVisible = false
    }
    
    private fun showPlayerDialog() {
        val playerDialogFragment = PlayerSettingsDialogFragment()
        val bundle = Bundle()
        
        bundle.putInt(PlayerSettingsDialogFragment.KEY_INITIAL_COLOR, binding.etName.currentTextColor)
        val name =
            if (binding.etName.text == null)
                activityViewModel.getPlayerName(playerId)
            else
                binding.etName.text.toString()
        bundle.putString(PlayerSettingsDialogFragment.KEY_INITIAL_NAME, name)
        
        playerDialogFragment.arguments = bundle
        playerDialogFragment.setPlayerDialogListener(this)
        
        playerDialogFragment.show(requireActivity().supportFragmentManager, PlayerSettingsDialogFragment.TAG)
    }
    
    private fun askConfirmRestorePlayerPoints() {
        var name = (binding.etName.text ?: "").toString()
        if (name.isBlank())
            name = getString(R.string.your)
        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
            .setTitle(R.string.are_you_sure)
            .setMessage(String.format(getString(R.string.restart_x_points_to_y), name, initialPoints))
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes) { _, _ -> restorePlayerPoints() }
            .create()
            .show()
    }
    
    private fun restorePlayerPoints() {
        points = initialPoints
        updatePoints()
    }
    
    private fun askConfirmRestoreGamePlayersPoints() {
        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
            .setTitle(R.string.are_you_sure)
            .setMessage(String.format(getString(R.string.restart_all_points_to_y), initialPoints))
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes) { _, _ -> activityViewModel.restoreGamePlayersPoints(numberOfPlayersInThisGame) }
            .create()
            .show()
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
            Thread { activityViewModel.savePlayerPoints(points, playerId) }.run()
        }
        val updatePointsAnimation = MyAnimationUtils.getUpdateShieldPointsAnimation(
            binding.tvPointsPlayer,
            binding.tvPointsForAnimation,
            points,
            animationEndListener
        )
        binding.tvPointsForAnimation.startAnimation(updatePointsAnimation)
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
            activityViewModel.savePlayerPoints(points, playerId)
            updatePoints()
            repeatUpdateHandler.postDelayed(RepeatUpdater(), REP_DELAY.toLong())
        }
    }
    
}