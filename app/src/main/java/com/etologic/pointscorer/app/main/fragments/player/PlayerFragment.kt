package com.etologic.pointscorer.app.main.fragments.player

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_CANCEL
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.fragments.Game1PlayerFragment.Companion.GAME_1_PLAYER_1_ID
import com.etologic.pointscorer.app.main.fragments.player.settings_menu.PlayerSettingsDialogFragment
import com.etologic.pointscorer.app.main.fragments.player.settings_menu.PlayerSettingsDialogFragment.Companion.KEY_INITIAL_COLOR
import com.etologic.pointscorer.app.main.fragments.player.settings_menu.PlayerSettingsDialogFragment.Companion.KEY_INITIAL_NAME
import com.etologic.pointscorer.app.main.fragments.player.settings_menu.PlayerSettingsDialogFragment.Companion.KEY_INITIAL_POINTS
import com.etologic.pointscorer.app.main.fragments.player.settings_menu.PlayerSettingsDialogFragment.Companion.KEY_IS_ONE_PLAYER_FRAGMENT
import com.etologic.pointscorer.app.main.fragments.player.settings_menu.PlayerSettingsDialogFragment.PlayerDialogListener
import com.etologic.pointscorer.app.utils.MyAnimationUtils
import com.etologic.pointscorer.app.utils.dpToPx
import com.etologic.pointscorer.databinding.GamePlayerFragmentBinding
import java.util.*
import javax.inject.Inject

class PlayerFragment : BaseMainFragment() {

    @Inject
    internal lateinit var viewModelFactory: PlayerFragmentViewModelFactory
    private lateinit var viewModel: PlayerFragmentViewModel
    private var _binding: GamePlayerFragmentBinding? = null
    private val binding get() = _binding!!
    private var defaultPlayerColor: Int? = null
    private var playerNameSize = DEFAULT_PLAYER_NAME_SIZE
    private var playerNameMarginTop = DEFAULT_PLAYER_NAME_MARGIN_TOP
    private var playerPointsSize = DEFAULT_PLAYER_POINTS_SIZE
    private var playerPointsMarginTop = DEFAULT_PLAYER_POINTS_MARGIN_TOP
    private val upRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private val downRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private lateinit var upAuxPointsFadeOutAnimation: Animation
    private lateinit var downAuxPointsFadeOutAnimation: Animation
    private var lastUpCountPoints = 0
    private var lastDownCountPoints = 0
    private var isUpPressed = false /* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private var isDownPressed = false
    private var playerSettingsMenuDialogFragment: PlayerSettingsDialogFragment? = null
    private val playerSettingsMenuDialogFragmentListener = object : PlayerDialogListener {
        override fun onColorChanged(color: Int) { viewModel.savePlayerColor(color) }
        override fun onNameChanged(name: String) { viewModel.savePlayerName(name) }
        override fun onRestorePlayerPointsClicked() { viewModel.restorePlayerPoints() }
        override fun onRestoreAllPlayersPointsClicked() { activityViewModel.restoreOneGamePoints(viewModel.gamePlayersNum) }
    }

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
        viewModel.initScreen(arguments?.getInt(KEY_PLAYER_ID))
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlayerFragmentViewModel::class.java]
    }

    private fun initValues() {
        defaultPlayerColor = ContextCompat.getColor(requireContext(), R.color.white)
        arguments?.let { arguments ->
            playerNameSize = arguments.getInt(KEY_PLAYER_NAME_SIZE)
            playerNameMarginTop = arguments.getInt(KEY_PLAYER_NAME_MARGIN_TOP, 8)
            playerPointsSize = arguments.getInt(KEY_PLAYER_POINTS_SIZE)
        }
    }

    private fun initViews() {
        with(binding) {
            tvUpCount.textSize = playerPointsSize * 0.5f
            tvDownCount.textSize = playerPointsSize * 0.5f
            tvPointsPlayer.textSize = playerPointsSize.toFloat()
            tvPointsPlayer.setPadding(0, playerPointsMarginTop.dpToPx(resources), 0, 0)
            etName.setPadding(0, playerNameMarginTop.dpToPx(resources), 0, 0)
            etName.textSize = playerNameSize.toFloat()
            upAuxPointsFadeOutAnimation = MyAnimationUtils.getAuxPointsFadeOutAnimation {
                if (downAuxPointsFadeOutAnimation.hasEnded())
                    viewModel.countAnimationEnded()
            }
            downAuxPointsFadeOutAnimation = MyAnimationUtils.getAuxPointsFadeOutAnimation {
                if (upAuxPointsFadeOutAnimation.hasEnded())
                    viewModel.countAnimationEnded()
            }
            binding.tvUpCount.animation = upAuxPointsFadeOutAnimation
            binding.tvDownCount.animation = downAuxPointsFadeOutAnimation
            ivShield.startAnimation(MyAnimationUtils.shieldAnimation)
        }
    }

    private fun initObservers() {
        viewModel.livePlayerPoints().observe(viewLifecycleOwner) { updateShieldPoints(it) }
        viewModel.livePlayerCount().observe(viewLifecycleOwner) { updateCountPoints(it) }
        viewModel.livePlayerName().observe(viewLifecycleOwner) { updateName(it) }
        viewModel.livePlayerColor().observe(viewLifecycleOwner) { setTextsColor(it) }
        activityViewModel.liveShouldRestoreAllPoints.observe(viewLifecycleOwner) { restorePlayerPoints(it) }
    }

    private fun updateShieldPoints(points: Int) {
        binding.tvPointsPlayer.text = String.format(Locale.getDefault(), "%d", points)
    }

    private fun updateName(it: String) {
        binding.etName.setText(it)
    }

    private fun setTextsColor(color: Int) {
        with(binding) {
            etName.setTextColor(color)
            etName.setHintTextColor(color)
            tvPointsPlayer.setTextColor(color)
            ImageViewCompat.setImageTintList(ibMenu, ColorStateList.valueOf(color))
            ImageViewCompat.setImageTintList(ivShield, ColorStateList.valueOf(color))
        }
    }

    private fun updateCountPoints(count: Int) {
        if (viewModel.playerCountEnabled) {
            with(binding) {
                val countText = String.format("%+d", count)
                when {
                    count > 0 -> {
                        lastUpCountPoints = count
                        tvUpCount.text = countText
                        upAuxPointsFadeOutAnimation.start()
                    }
                    count < 0 -> {
                        lastDownCountPoints = count
                        tvDownCount.text = countText
                        downAuxPointsFadeOutAnimation.start()
                    }
                    else -> {
                        try {
                            if (lastUpCountPoints == 1) {
                                lastUpCountPoints = 0
                                tvUpCount.text = countText
                                upAuxPointsFadeOutAnimation.start()
                            } else if (lastDownCountPoints == -1) {
                                lastDownCountPoints = 0
                                tvDownCount.text = countText
                                downAuxPointsFadeOutAnimation.start()
                            }
                        } catch (nfe: NumberFormatException) {
                            Toast.makeText(context, nfe.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun restorePlayerPoints(playersNum: Int?) {
        if (playersNum == viewModel.gamePlayersNum)
            viewModel.restorePlayerPoints()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        with(binding) {
            ibMenu.setOnClickListener {
                showPlayerDialog()
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
                viewModel.upClicked()
            }

            btDown.setOnClickListener {
                viewModel.downClicked()
            }
        }
    }

    private fun showPlayerDialog() {
        playerSettingsMenuDialogFragment = PlayerSettingsDialogFragment()
        val bundle = Bundle().apply { putInt(KEY_INITIAL_COLOR, binding.etName.currentTextColor) }
        bundle.putString(KEY_INITIAL_NAME, (binding.etName.text ?: viewModel.livePlayerName().value).toString())
        bundle.putInt(KEY_INITIAL_POINTS, activityViewModel.liveInitialPoints.value!!)
        bundle.putBoolean(KEY_IS_ONE_PLAYER_FRAGMENT, viewModel.playerId == GAME_1_PLAYER_1_ID)
        playerSettingsMenuDialogFragment?.arguments = bundle
        playerSettingsMenuDialogFragment?.setPlayerDialogListener(playerSettingsMenuDialogFragmentListener)
        playerSettingsMenuDialogFragment?.show(requireActivity().supportFragmentManager, PlayerSettingsDialogFragment.TAG)
    }

    override fun onResume() {
        super.onResume()
        playerSettingsMenuDialogFragment = parentFragmentManager.findFragmentByTag(PlayerSettingsDialogFragment.TAG)?.let {
            it as PlayerSettingsDialogFragment
        }
        playerSettingsMenuDialogFragment?.setPlayerDialogListener(playerSettingsMenuDialogFragmentListener)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    internal inner class UpCountRepeater : Runnable {

        override fun run() {
            if (isUpPressed) {
                viewModel.upClicked()
                upRepeatUpdateHandler.postDelayed(UpCountRepeater(), REP_DELAY.toLong())
            }
        }
    }

    internal inner class DownCountRepeater : Runnable {

        override fun run() {
            if (isDownPressed) {
                viewModel.downClicked()
                downRepeatUpdateHandler.postDelayed(DownCountRepeater(), REP_DELAY.toLong())
            }
        }
    }


    companion object {

        const val KEY_PLAYER_ID = "key_player_id"
        const val KEY_PLAYER_NAME_SIZE = "key_player_name_size"
        const val KEY_PLAYER_NAME_MARGIN_TOP = "key_player_name_margin_top"
        const val KEY_PLAYER_POINTS_SIZE = "key_player_points_size"
        const val REP_DELAY = 100
        private const val DEFAULT_PLAYER_NAME_SIZE = 16
        private const val DEFAULT_PLAYER_NAME_MARGIN_TOP = 0
        private const val DEFAULT_PLAYER_POINTS_SIZE = 48
        private const val DEFAULT_PLAYER_POINTS_MARGIN_TOP = 0
    }

}