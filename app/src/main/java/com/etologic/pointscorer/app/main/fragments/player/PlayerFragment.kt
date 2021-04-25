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
import android.view.animation.Animation
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.R
import com.etologic.pointscorer.R.menu
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.fragments.Game1PlayerFragment.Companion.GAME_1_PLAYER_1_ID
import com.etologic.pointscorer.app.main.fragments.player.PlayerSettingsDialogFragment.PlayerDialogListener
import com.etologic.pointscorer.app.utils.MyAnimationUtils
import com.etologic.pointscorer.app.utils.MyAnimationUtils.getUpdateShieldPointsAnimation
import com.etologic.pointscorer.app.utils.MyConversionUtils
import com.etologic.pointscorer.databinding.GamePlayerFragmentBinding
import kotlinx.android.synthetic.main.game_player_fragment.*
import javax.inject.Inject

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
    @Inject
    internal lateinit var viewModelFactory: PlayerFragmentViewModelFactory
    private lateinit var viewModel: PlayerFragmentViewModel
    private var _binding: GamePlayerFragmentBinding? = null
    private val binding get() = _binding!!
    private var playerId = 0
    private var auxPoints: Int? = null
    private var defaultPlayerColor: Int? = null
    private var playerNameSize = 16
    private var playerNameMarginTop = 0
    private var playerPointsSize = 48
    private var playerPointsMarginTop = 0
    private var popup: PopupMenu? = null
    private lateinit var upRepeatUpdateHandler: Handler
    private lateinit var downRepeatUpdateHandler: Handler
    private lateinit var upAuxPointsFadeOutAnimation: Animation
    private lateinit var downAuxPointsFadeOutAnimation: Animation
    private var isUpPressed = false /* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private var isDownPressed = false
    private var downCount = 0
    private var upCount = 0
    private var numberOfPlayersInTheGame = 0
    
    //EVENTS
    override fun onColorChanged(color: Int) {
        viewModel.savePlayerColor(color, playerId)
    }
    
    override fun onNameChanged(name: String) {
        viewModel.savePlayerName(playerId, name)
    }
    
    //LIFECYCLE
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GamePlayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initValues()
        initViews()
        initObservers()
        initListeners()
        viewModel.getPlayerPoints(playerId)
        viewModel.getPlayerName(playerId)
        viewModel.getPlayerColor(playerId)
    }
    
    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayerFragmentViewModel::class.java)
    }
    
    private fun initValues() {
        defaultPlayerColor = ContextCompat.getColor(requireContext(), R.color.white)
        upRepeatUpdateHandler = binding.root.handler
        downRepeatUpdateHandler = binding.root.handler
        arguments?.let { arguments ->
            playerId = arguments.getInt(KEY_PLAYER_ID)
            numberOfPlayersInTheGame = playerId / 10
            playerNameSize = arguments.getInt(KEY_PLAYER_NAME_SIZE)
            playerNameMarginTop = arguments.getInt(KEY_PLAYER_NAME_MARGIN_TOP, 8)
            playerPointsSize = arguments.getInt(KEY_PLAYER_POINTS_SIZE)
        }
        upAuxPointsFadeOutAnimation = MyAnimationUtils.getAuxPointsFadeOutAnimation {
            binding.tvUpCount.text = ""
            upCount = 0
        }
        downAuxPointsFadeOutAnimation = MyAnimationUtils.getAuxPointsFadeOutAnimation {
            binding.tvDownCount.text = ""
            downCount = 0
        }
    }
    
    private fun initViews() {
        with(binding) {
            tvUpCount.textSize = playerPointsSize * 0.5f
            tvDownCount.textSize = playerPointsSize * 0.5f
            tvPointsPlayer.textSize = playerPointsSize.toFloat()
            tvPointsForAnimation.textSize = playerPointsSize.toFloat()
            tvPointsPlayer.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
            tvPointsForAnimation.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
            etName.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerNameMarginTop), 0, 0)
            etName.textSize = playerNameSize.toFloat()
            ivShield.startAnimation(MyAnimationUtils.getShieldAnimation())
        }
    }
    
    private fun initObservers() {
        viewModel.livePlayerPoints().observe(viewLifecycleOwner, { initShieldPoints(it) })
        viewModel.livePlayerName().observe(viewLifecycleOwner, { binding.etName.setText(it) })
        viewModel.livePlayerColor().observe(viewLifecycleOwner, { setTextsColor(it) })
        activityViewModel.liveShouldRestoreAllPoints().observe(viewLifecycleOwner, { if (it == playerId / 10) restorePlayerPoints() })
    }
    
    private fun initShieldPoints(points: Int) {
        auxPoints = points
        updatePoints()
        viewModel.livePlayerPoints().removeObservers(viewLifecycleOwner)
    }
    
    private fun setTextsColor(color: Int) {
        with(binding) {
            etName.setTextColor(color)
            etName.setHintTextColor(color)
            tvPointsPlayer.setTextColor(color)
            tvPointsForAnimation.setTextColor(color)
        }
    }
    
    private fun restorePlayerPoints() {
        auxPoints = activityViewModel.liveInitialPoints().value!!
        updatePoints()
    }
    
    private fun updatePoints() {
        binding.tvUpCount.text = if (upCount != 0) String.format("%+d", upCount) else ""
        binding.tvDownCount.text = if (downCount != 0) String.format("%+d", downCount) else ""
        binding.tvPointsForAnimation.startAnimation(
            getUpdateShieldPointsAnimation(
                binding.tvPointsPlayer,
                binding.tvPointsForAnimation,
                auxPoints!!
            ) {
                viewModel.savePlayerPoints(auxPoints!!, playerId)
            })
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
                isUpPressed = true
                upRepeatUpdateHandler.post(UpCountRepeater())
            }
            
            btDown.setOnLongClickListener {
                isDownPressed = true
                downRepeatUpdateHandler.post(DownCountRepeater())
            }
            
            btUp.setOnTouchListener { _, motionEvent ->
                val isActionUp = motionEvent.action == ACTION_UP
                val isActionCancel = motionEvent.action == ACTION_CANCEL
                if (isUpPressed && (isActionUp || isActionCancel))
                    isUpPressed = false
                false
            }
            
            btDown.setOnTouchListener { _, motionEvent ->
                val isActionUp = motionEvent.action == ACTION_UP
                val isActionCancel = motionEvent.action == ACTION_CANCEL
                if (isDownPressed && (isActionUp || isActionCancel))
                    isDownPressed = false
                false
            }
            
            btUp.setOnClickListener {
                incrementAuxPointsAndCount()
                updatePoints()
                tvUpCount.startAnimation(upAuxPointsFadeOutAnimation)
            }
            
            btDown.setOnClickListener {
                decrementAuxPointsAndCount()
                updatePoints()
                tvDownCount.startAnimation(downAuxPointsFadeOutAnimation)
            }
        }
    }
    
    private fun incrementAuxPointsAndCount() {
        if (auxPoints!! < MAX_POINTS) {
            auxPoints = auxPoints!! + 1
            upCount++
        }
    }
    
    private fun decrementAuxPointsAndCount() {
        if (auxPoints!! > MIN_POINTS) {
            auxPoints = auxPoints!! - 1
            downCount--
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
                viewModel.livePlayerName().value
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
            .setMessage(String.format(getString(R.string.restart_x_points_to_y), name, activityViewModel.liveInitialPoints().value))
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ -> restorePlayerPoints() }
            .create()
            .show()
    }
    
    private fun askConfirmRestoreGamePlayersPoints() {
        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
            .setTitle(R.string.are_you_sure)
            .setMessage(String.format(getString(R.string.restart_all_points_to_y), activityViewModel.liveInitialPoints().value))
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ -> activityViewModel.restoreOneGamePoints(numberOfPlayersInTheGame) }
            .create()
            .show()
    }
    
    //INNER CLASSES
    internal inner class UpCountRepeater : Runnable {
        
        override fun run() {
            if (binding.tvUpCount.animation == null)
                binding.tvUpCount.animation = upAuxPointsFadeOutAnimation
            
            upAuxPointsFadeOutAnimation.start()
            
            if (isUpPressed) {
                incrementAuxPointsAndCount()
                updatePoints()
                upRepeatUpdateHandler.postDelayed(UpCountRepeater(), REP_DELAY.toLong())
            }
        }
    }
    
    internal inner class DownCountRepeater : Runnable {
        
        override fun run() {
            if (binding.tvDownCount.animation == null)
                binding.tvDownCount.animation = downAuxPointsFadeOutAnimation
            
            downAuxPointsFadeOutAnimation.start()
            
            if (isDownPressed) {
                decrementAuxPointsAndCount()
                updatePoints()
                downRepeatUpdateHandler.postDelayed(DownCountRepeater(), REP_DELAY.toLong())
            }
        }
    }
    
}