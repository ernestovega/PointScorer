package com.etologic.pointscorer.app.games.base.player

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.RelativeLayout.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.canhub.cropper.*
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.base.BaseMainFragment
import com.etologic.pointscorer.app.common.utils.MyAnimationUtils
import com.etologic.pointscorer.app.common.utils.dpToPx
import com.etologic.pointscorer.app.games.base.game.GameViewModel
import com.etologic.pointscorer.app.games.base.player.PlayerFragment.*
import com.etologic.pointscorer.bussiness.model.entities.Player
import com.etologic.pointscorer.databinding.PlayerFragmentBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.FileNotFoundException
import java.util.*

class PlayerFragment : BaseMainFragment() {

    data class PlayerFragmentInitialData(
        val nameSize: Int?,
        val nameMarginTop: Int?,
        val pointsSize: Int?,
        val positiveAuxPointsMarginTop: Int?,
        val auxPointsMargin: Int?,
    )

    companion object {
        fun newInstance(
            playerId: Int,
            playerFragmentInitialData: PlayerFragmentInitialData? = null
        ) =
            PlayerFragment().apply {
                this.playerId = playerId
                playerFragmentInitialData?.nameSize?.let { this.playerNameSize = it }
                playerFragmentInitialData?.nameMarginTop?.let { this.playerNameMarginTop = it }
                playerFragmentInitialData?.pointsSize?.let { this.playerPointsSize = it }
                playerFragmentInitialData?.positiveAuxPointsMarginTop?.let {
                    this.positiveAuxPointsMarginTop = it
                }
                playerFragmentInitialData?.auxPointsMargin?.let { this.auxPointsMargin = it }
            }

        const val REP_DELAY = 100L
        private const val DEFAULT_PLAYER_NAME_SIZE = 16
        private const val DEFAULT_PLAYER_NAME_MARGIN_TOP = 8
        private const val DEFAULT_PLAYER_POINTS_SIZE = 48
        private const val DEFAULT_PLAYER_POSITIVE_AUX_POINTS_MARGIN_TOP = 0
        private const val DEFAULT_PLAYER_AUX_POINTS_MARGIN = 16
    }

    private var playerId: Int? = null
    private var playerNameSize: Int = DEFAULT_PLAYER_NAME_SIZE
    private var playerNameMarginTop: Int = DEFAULT_PLAYER_NAME_MARGIN_TOP
    private var playerPointsSize: Int = DEFAULT_PLAYER_POINTS_SIZE
    private var positiveAuxPointsMarginTop: Int = DEFAULT_PLAYER_POSITIVE_AUX_POINTS_MARGIN_TOP
    private var auxPointsMargin: Int = DEFAULT_PLAYER_AUX_POINTS_MARGIN

    private var _binding: PlayerFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()
    private val gameViewModel: GameViewModel by hiltNavGraphViewModels(R.id.a_main_nav_graph)
    private var auxPointsPositiveRotation: Float? = null
    private var auxPointsNegativeRotation: Float? = null
    private var greenColor: Int? = null
    private var redColor: Int? = null
    private var defaultPlayerColor: Int? = null
    private val upRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private val downRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private lateinit var auxPointsFadeOutAnimation: Animation
    private var auxPoints = 0
    private var isUpPressed = false
    private var isDownPressed = false

    private val cropImageActivityResultLauncher =
        registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful && result.uriContent != null) {
                gameViewModel.savePlayerBackground(playerId!!, result.uriContent!!)
            } else {
                FirebaseCrashlytics.getInstance().recordException(Throwable(result.error))
                Toast.makeText(requireContext(), getString(R.string.ups), Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initViews()
        initViewModels()
        initListeners()

        viewModel.loadScreen()
    }

    private fun initValues() {
        auxPointsPositiveRotation =
            ResourcesCompat.getFloat(resources, R.dimen.auxPointsPositiveRotationDegrees)
        auxPointsNegativeRotation =
            ResourcesCompat.getFloat(resources, R.dimen.auxPointsNegativeRotationDegrees)
        greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        redColor = ContextCompat.getColor(requireContext(), R.color.red)
        defaultPlayerColor = ContextCompat.getColor(requireContext(), R.color.white)
        auxPointsFadeOutAnimation = MyAnimationUtils.getAuxPointsFadeOutAnimation {
            if (auxPointsFadeOutAnimation.hasEnded()) {
                viewModel.auxPointsAnimationEnded()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            tvAuxPoints.textSize = playerPointsSize * 0.5f
            tvPointsPlayer.textSize = playerPointsSize.toFloat()
            etName.setPadding(0, playerNameMarginTop.dpToPx(resources), 0, 0)
            etName.textSize = playerNameSize.toFloat()
            tvAuxPoints.animation = auxPointsFadeOutAnimation
        }
    }

    private fun initViewModels() {
        viewModel.playerPointsObservable().observe(viewLifecycleOwner) { updatePoints(it) }
        viewModel.playerAuxPointsObservable().observe(viewLifecycleOwner) { updateCountPoints(it) }
        viewModel.playerNameObservable().observe(viewLifecycleOwner) { updateName(it) }
        viewModel.playerColorObservable().observe(viewLifecycleOwner) { setTextsColor(it) }
        viewModel.playerBackgroundObservable().observe(viewLifecycleOwner) { setBackground(it) }

        gameViewModel.changeBackgroundRequestedObservable()
            .observe(viewLifecycleOwner) { changeBackgroundRequestedObserver(it) }
        gameViewModel.restorePlayerPointsRequestedObservable()
            .observe(viewLifecycleOwner) { restorePlayerPointsObserver(it) }
        gameViewModel.playerNameChangedObservable()
            .observe(viewLifecycleOwner) { playerNameChangedObserver(it) }
        gameViewModel.playerColorChangedObservable()
            .observe(viewLifecycleOwner) { playerColorChangedObserver(it) }
        gameViewModel.playerBackgroundChangedObservable()
            .observe(viewLifecycleOwner) { playerBackgroundChangedObserver(it) }
        gameViewModel.playerPointsRestoredObservable()
            .observe(viewLifecycleOwner) { playerPointsRestoredObserver(it) }
    }

    private fun restorePlayerPointsObserver(playerIdToChange: Boolean?) {
        if (playerIdToChange == true) {
            gameViewModel.restorePlayerPoints(playerId!!)
        }
    }

    private fun updatePoints(points: Int) {
        binding.tvPointsPlayer.text = points.toString()
    }

    private fun updateCountPoints(count: Int) {
        with(binding) {

            fun setupPositiveAuxPointsView() {
                tvAuxPoints.apply {
                    ConstraintSet().apply {
                        clone(binding.root)
                        connect(id, BOTTOM, UNSET, BOTTOM)
                        connect(id, END, UNSET, END)
                        connect(id, START, PARENT_ID, START, auxPointsMargin)
                        connect(id, TOP, etName.id, BOTTOM, positiveAuxPointsMarginTop)
                        applyTo(binding.root)
                    }
                    auxPointsPositiveRotation?.let { rotation = it }
                    greenColor?.let { setTextColor(it) }
                }
            }

            fun setupNegativeAuxPointsView() {
                tvAuxPoints.apply {
                    ConstraintSet().apply {
                        clone(binding.root)
                        connect(id, BOTTOM, PARENT_ID, BOTTOM, auxPointsMargin)
                        connect(id, END, UNSET, END)
                        connect(id, START, PARENT_ID, START, auxPointsMargin)
                        connect(id, TOP, UNSET, BOTTOM)
                        applyTo(binding.root)
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
                tvAuxPoints.text = if (count == 0) "0" else String.format("%+d", count)
                auxPointsFadeOutAnimation.start()
            }
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
                    ivCustomBackground.visibility = View.GONE
                    ivShield.visibility = View.VISIBLE
                }
            }

            fun showCustomBackground(customBgUri: Uri) {
                try {
                    val newBackgroundDrawable = Drawable.createFromStream(
                        requireContext().contentResolver.openInputStream(customBgUri),
                        bgUri.toString()
                    )
                    ivCustomBackground.setImageDrawable(newBackgroundDrawable)
                    ivShield.visibility = View.GONE
                    ivCustomBackground.visibility = View.VISIBLE
                } catch (e: FileNotFoundException) {
                    FirebaseCrashlytics.getInstance().recordException(e)
                    showDefaultBackground()
                }
            }

            bgUri?.let { showCustomBackground(it) } ?: showDefaultBackground()
        }
    }

    private fun changeBackgroundRequestedObserver(playerIdToChange: Int?) {
        if (playerIdToChange == playerId) {
            cropImageActivityResultLauncher.launch(
                CropImageContractOptions(
                    null, CropImageOptions(
                        imageSourceIncludeGallery = true,
                        imageSourceIncludeCamera = false,
                        guidelines = CropImageView.Guidelines.ON
                    )
                )
            )
        }
    }

    private fun playerNameChangedObserver(player: Player) {
        if (player.id == playerId) {
            viewModel.loadPlayerName()
        }
    }

    private fun playerColorChangedObserver(player: Player) {
        if (player.id == playerId) {
            viewModel.loadPlayerColor()
        }
    }

    private fun playerBackgroundChangedObserver(player: Player) {
        if (player.id == playerId) {
            viewModel.loadPlayerBackground()
        }
    }

    private fun playerPointsRestoredObserver(player: Player) {
        if (player.id == playerId) {
            viewModel.loadPlayerPoints()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        with(binding) {
            ibMenu.setOnClickListener {
                gameViewModel.shouldShowGameInterstitialAd = false
                gameViewModel.showPlayerSettingsMenuRequested(
                    GameViewModel.PlayerSettingsMenuInitialData(
                        playerId = playerId!!,
                        playerInitialName = viewModel.playerNameObservable().value
                            ?: getString(R.string.player_name),
                        playerInitialColor = viewModel.playerColorObservable().value
                            ?: defaultPlayerColor!!,
                        playerHasCustomBackground = viewModel.playerBackgroundObservable().value != null,
                    )
                )
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
                    gameViewModel.onPointsTouched()
                }
                val isActionUp = motionEvent.action == ACTION_UP
                val isActionCancel = motionEvent.action == ACTION_CANCEL
                if (isUpPressed && (isActionUp || isActionCancel)) {
                    isUpPressed = false
                }
                false
            }

            btDown.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == ACTION_DOWN) {
                    gameViewModel.onPointsTouched()
                }
                val isActionUp = motionEvent.action == ACTION_UP
                val isActionCancel = motionEvent.action == ACTION_CANCEL
                if (isDownPressed && (isActionUp || isActionCancel)) {
                    isDownPressed = false
                }
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    /* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private inner class UpCountRepeater : Runnable {
        override fun run() {
            if (isUpPressed) {
                viewModel.upClicked()
                upRepeatUpdateHandler.postDelayed(UpCountRepeater(), REP_DELAY)
            }
        }
    }

    private inner class DownCountRepeater : Runnable {
        override fun run() {
            if (isDownPressed) {
                viewModel.downClicked()
                downRepeatUpdateHandler.postDelayed(DownCountRepeater(), REP_DELAY)
            }
        }
    }

}