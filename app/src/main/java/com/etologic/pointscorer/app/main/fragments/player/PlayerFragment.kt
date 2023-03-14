package com.etologic.pointscorer.app.main.fragments.player

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent.*
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.widget.RelativeLayout.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.exceptions.PlayerIdNotFoundException
import com.etologic.pointscorer.app.common.utils.MyAnimationUtils
import com.etologic.pointscorer.app.common.utils.dpToPx
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.fragments.player.player_settings.PlayerSettingsMenuDialogFragment
import com.etologic.pointscorer.databinding.GamePlayerFragmentBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.FileNotFoundException
import java.util.*


class PlayerFragment : BaseMainFragment() {

    interface PlayerFragmentListener {
        fun onPointsTouched()
    }

    companion object {
        fun getNewInstance(data: Bundle, listener: PlayerFragmentListener) =
            PlayerFragment().apply {
                arguments = data
                playerFragmentListener = listener
            }

        const val KEY_PLAYER_ID = "key_player_id"
        const val KEY_GAME_NUM_PLAYERS = "key_game_num_players"
        const val KEY_PLAYER_NAME_SIZE = "key_player_name_size"
        const val KEY_PLAYER_NAME_MARGIN_TOP = "key_player_name_margin_top"
        const val KEY_PLAYER_POINTS_SIZE = "key_player_points_size"
        const val REP_DELAY = 100
        private const val DEFAULT_PLAYER_NAME_SIZE = 16
        private const val DEFAULT_PLAYER_NAME_MARGIN_TOP = 0
        private const val DEFAULT_PLAYER_POINTS_SIZE = 48
        private const val DEFAULT_PLAYER_POINTS_MARGIN_TOP = 0
    }

    private val viewModel: PlayerFragmentViewModel by viewModels()
    private var _binding: GamePlayerFragmentBinding? = null
    private val binding get() = _binding!!
    private var playerFragmentListener: PlayerFragmentListener? = null
    private var auxPointsMarginSize: Int? = null
    private var auxPointsPositiveRotation: Float? = null
    private var auxPointsNegativeRotation: Float? = null
    private var greenColor: Int? = null
    private var redColor: Int? = null
    private var defaultPlayerColor: Int? = null
    private var playerNameSize = DEFAULT_PLAYER_NAME_SIZE
    private var playerNameMarginTop = DEFAULT_PLAYER_NAME_MARGIN_TOP
    private var playerPointsSize = DEFAULT_PLAYER_POINTS_SIZE
    private var playerPointsMarginTop = DEFAULT_PLAYER_POINTS_MARGIN_TOP
    private val upRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private val downRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private lateinit var auxPointsFadeOutAnimation: Animation
    private var auxPoints = 0
    private var isUpPressed =
        false/* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private var isDownPressed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GamePlayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initViews()
        initObservers()
        initListeners()
        loadScreen()
    }

    private fun loadScreen() {
        val playerId = arguments?.getInt(KEY_PLAYER_ID)
        val gamePlayersNum = arguments?.getInt(KEY_GAME_NUM_PLAYERS)
        if (playerId == null || gamePlayersNum == null) {
            FirebaseCrashlytics.getInstance().recordException(PlayerIdNotFoundException())
            Toast.makeText(requireContext(), getString(R.string.ups), Toast.LENGTH_SHORT).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    addCallback(object : Toast.Callback() {
                        override fun onToastShown() {}
                        override fun onToastHidden() {
                            findNavController().navigateUp()
                        }
                    })
                }
                show()
            }
        } else {
            viewModel.initScreen(playerId, gamePlayersNum)
        }
    }

    private fun initValues() {
        auxPointsMarginSize = ResourcesCompat.getFloat(resources, R.dimen.auxPointsMargin).toInt().dpToPx(resources)
        auxPointsPositiveRotation = ResourcesCompat.getFloat(resources, R.dimen.auxPointsPositiveRotationDegrees)
        auxPointsNegativeRotation = ResourcesCompat.getFloat(resources, R.dimen.auxPointsNegativeRotationDegrees)
        greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        redColor = ContextCompat.getColor(requireContext(), R.color.red)
        defaultPlayerColor = ContextCompat.getColor(requireContext(), R.color.white)
        arguments?.let { arguments ->
            playerNameSize = arguments.getInt(KEY_PLAYER_NAME_SIZE)
            playerNameMarginTop = arguments.getInt(KEY_PLAYER_NAME_MARGIN_TOP, 8)
            playerPointsSize = arguments.getInt(KEY_PLAYER_POINTS_SIZE)
        }
    }

    private fun initViews() {
        with(binding) {
            tvAuxPoints.textSize = playerPointsSize * 0.5f
            tvPointsPlayer.textSize = playerPointsSize.toFloat()
            tvPointsPlayer.setPadding(0, playerPointsMarginTop.dpToPx(resources), 0, 0)
            etName.setPadding(0, playerNameMarginTop.dpToPx(resources), 0, 0)
            etName.textSize = playerNameSize.toFloat()
            auxPointsFadeOutAnimation = MyAnimationUtils.getAuxPointsFadeOutAnimation {
                if (auxPointsFadeOutAnimation.hasEnded()) {
                    viewModel.auxPointsAnimationEnded()
                }
            }
            binding.tvAuxPoints.animation = auxPointsFadeOutAnimation
        }
    }

    private fun initObservers() {
        viewModel.livePlayerPoints().observe(viewLifecycleOwner) { updatePoints(it) }
        viewModel.livePlayerAuxPoints().observe(viewLifecycleOwner) { updateCountPoints(it) }
        viewModel.livePlayerName().observe(viewLifecycleOwner) { updateName(it) }
        viewModel.livePlayerColor().observe(viewLifecycleOwner) { setTextsColor(it) }
        viewModel.livePlayerBackground().observe(viewLifecycleOwner) { setBackground(it) }
        activityViewModel.gamePlayersPointsRestoredObservable()
            .observe(viewLifecycleOwner) { gamePlayersPointsRestoredObserver(it) }
    }

    private fun updatePoints(points: Int) {
        binding.tvPointsPlayer.text = String.format(Locale.getDefault(), "%d", points)
    }

    private fun updateCountPoints(count: Int) {

        fun setupPositiveAuxPointsView() {
            binding.tvAuxPoints.apply {
                layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    removeRule(ALIGN_PARENT_BOTTOM)
                    removeRule(ALIGN_PARENT_END)
                    addRule(BELOW, R.id.etName)
                    addRule(ALIGN_PARENT_START)
                    auxPointsMarginSize?.let { setMargins(it, 0, it, it) }
                }
                auxPointsPositiveRotation?.let { rotation = it }
                greenColor?.let { setTextColor(it) }
            }
        }

        fun setupNegativeAuxPointsView() {
            binding.tvAuxPoints.apply {
                layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    removeRule(BELOW)
                    removeRule(ALIGN_PARENT_TOP)
                    removeRule(ALIGN_PARENT_START)
                    addRule(ALIGN_PARENT_BOTTOM)
                    addRule(ALIGN_PARENT_END)
                    auxPointsMarginSize?.let { setMargins(it, it, it, it) }
                }
                auxPointsNegativeRotation?.let { rotation = it }
                redColor?.let { setTextColor(it) }
            }
        }

        if (viewModel.playerAuxPointsEnabled) {
            auxPoints = count
            when {
                count > 0 -> setupPositiveAuxPointsView()
                count < 0 -> setupNegativeAuxPointsView()
            }
            binding.tvAuxPoints.text = if (count == 0) "0" else String.format("%+d", count)
            auxPointsFadeOutAnimation.start()
        }
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

    private fun setBackground(bgUri: Uri?) {
        with(binding) {

            fun showDefaultBackground() {
                with(binding) {
                    ivCustomBackground.visibility = GONE
                    ivShield.visibility = VISIBLE
                }
            }

            fun showCustomBackground(customBgUri: Uri) {
                try {
                    val newBackgroundDrawable = Drawable.createFromStream(
                        requireContext().contentResolver.openInputStream(customBgUri),
                        bgUri.toString()
                    )
                    ivCustomBackground.setImageDrawable(newBackgroundDrawable)
                    ivShield.visibility = GONE
                    ivCustomBackground.visibility = VISIBLE
                } catch (e: FileNotFoundException) {
                    FirebaseCrashlytics.getInstance().recordException(e)
                    showDefaultBackground()
                }
            }

            bgUri?.let { showCustomBackground(it) } ?: showDefaultBackground()
        }
    }

    private fun gamePlayersPointsRestoredObserver(playersNum: Int) {
        if (playersNum == viewModel.gamePlayersNum) {
            viewModel.loadPlayerPoints()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        with(binding) {
            ibMenu.setOnClickListener {
                showPlayerSettingsMenuDialog()
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
                if (motionEvent.action == ACTION_DOWN) {
                    playerFragmentListener?.onPointsTouched()
                }
                val isActionUp = motionEvent.action == ACTION_UP
                val isActionCancel = motionEvent.action == ACTION_CANCEL
                if (isUpPressed && (isActionUp || isActionCancel))
                    isUpPressed = false
                false
            }

            btDown.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == ACTION_DOWN) {
                    playerFragmentListener?.onPointsTouched()
                }
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

    private fun showPlayerSettingsMenuDialog() {
        activityViewModel.shouldShowGameInterstitialAd = false
        val bundle = Bundle().apply { putInt(PlayerSettingsMenuDialogFragment.KEY_PLAYER_ID, viewModel.playerId) }
        findNavController().navigate(R.id.playerSettingsMenuDialogFragment, bundle)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private inner class UpCountRepeater : Runnable {
        override fun run() {
            if (isUpPressed) {
                viewModel.upClicked()
                upRepeatUpdateHandler.postDelayed(UpCountRepeater(), REP_DELAY.toLong())
            }
        }
    }

    private inner class DownCountRepeater : Runnable {
        override fun run() {
            if (isDownPressed) {
                viewModel.downClicked()
                downRepeatUpdateHandler.postDelayed(DownCountRepeater(), REP_DELAY.toLong())
            }
        }
    }

}