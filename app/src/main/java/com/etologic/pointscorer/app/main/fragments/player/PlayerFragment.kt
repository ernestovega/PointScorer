package com.etologic.pointscorer.app.main.fragments.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import java.util.*
import javax.inject.Inject

class PlayerFragment : BaseMainFragment() {

    companion object {

        const val UNABLED_COUNT = -1_000
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
    private var defaultPlayerColor: Int? = null
    private var playerNameSize = 16
    private var playerNameMarginTop = 0
    private var playerPointsSize = 48
    private var playerPointsMarginTop = 0
    private var popup: PopupMenu? = null
    private val upRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private val downRepeatUpdateHandler = Handler(Looper.getMainLooper())
    private lateinit var upAuxPointsFadeOutAnimation: Animation
    private lateinit var downAuxPointsFadeOutAnimation: Animation
    private var isUpPressed = false /* https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed */
    private var isDownPressed = false

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
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayerFragmentViewModel::class.java)
    }

    private fun initValues() {
        defaultPlayerColor = ContextCompat.getColor(requireContext(), R.color.white)
        arguments?.let { arguments ->
            viewModel.playerId = arguments.getInt(KEY_PLAYER_ID)
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
            tvPointsForAnimation.textSize = playerPointsSize.toFloat()
            tvPointsPlayer.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
            tvPointsForAnimation.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerPointsMarginTop), 0, 0)
            etName.setPadding(0, MyConversionUtils.dpToPx(requireContext(), playerNameMarginTop), 0, 0)
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
        viewModel.livePlayerName().observe(viewLifecycleOwner) { updateName(it) }
        viewModel.livePlayerColor().observe(viewLifecycleOwner) { setTextsColor(it) }
        viewModel.livePlayerCount().observe(viewLifecycleOwner) { updateCountPoints(it) }
        activityViewModel.liveShouldRestoreAllPoints.observe(viewLifecycleOwner) { restorePlayerPoints(it) }
    }

    private fun updateShieldPoints(points: Int) {
        if (viewModel.playerAnimatePoints) {
            tvPointsForAnimation.startAnimation(getUpdateShieldPointsAnimation(tvPointsPlayer, tvPointsForAnimation, points))
        } else {
            tvPointsPlayer.text = String.format(Locale.getDefault(), "%d", points)
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
            tvPointsForAnimation.setTextColor(color)
        }
    }

    private fun updateCountPoints(count: Int) {
        if (count != UNABLED_COUNT) {
            with(binding) {
                val countText by lazy { String.format("%+d", count) }
                when {
                    count > 0 -> {
                        tvUpCount.text = countText
                        upAuxPointsFadeOutAnimation.start()
                    }
                    count < 0 -> {
                        tvDownCount.text = countText
                        downAuxPointsFadeOutAnimation.start()
                    }
                    else -> {
                        try {
                            if (tvUpCount.text.toString().toInt() == 1) {
                                tvUpCount.text = countText
                                downAuxPointsFadeOutAnimation.start()
                            } else if (tvDownCount.text.toString().toInt() == -1) {
                                tvDownCount.text = countText
                                downAuxPointsFadeOutAnimation.start()
                            }
                        } catch (nfe: NumberFormatException) {
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
                viewModel.upClicked()
            }

            btDown.setOnClickListener {
                viewModel.downClicked()
            }
        }
    }

    private fun buildPopupMenu() {
        popup = PopupMenu(requireActivity(), binding.ibMenu)
        popup?.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_edit_player -> showPlayerDialog()
                R.id.menu_restart_points -> askConfirmRestorePlayerPoints()
                R.id.menu_restart_game_points -> askConfirmRestoreGamePlayersPoints()
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
        popup?.inflate(menu.game_player_menu)
        if (viewModel.playerId == GAME_1_PLAYER_1_ID)
            popup?.menu?.getItem(RESTORE_ALL_POINTS_ITEM_INDEX)?.isVisible = false
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
        playerDialogFragment.setPlayerDialogListener(object : PlayerDialogListener {

            override fun onColorChanged(color: Int) {
                viewModel.savePlayerColor(color)
            }

            override fun onNameChanged(name: String) {
                viewModel.savePlayerName(name)
            }

            override fun onUseAnimationChanged(animate: Boolean) {
                viewModel.animatePoints(animate)
            }
        })

        playerDialogFragment.show(requireActivity().supportFragmentManager, PlayerSettingsDialogFragment.TAG)
    }

    private fun askConfirmRestorePlayerPoints() {
        var name = (binding.etName.text ?: "").toString()
        if (name.isBlank())
            name = getString(R.string.your)
        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
            .setTitle(R.string.are_you_sure)
            .setMessage(String.format(getString(R.string.restart_x_points_to_y), name, activityViewModel.liveInitialPoints.value))
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ -> restorePlayerPoints(viewModel.gamePlayersNum) }
            .create()
            .show()
    }

    private fun askConfirmRestoreGamePlayersPoints() {
        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
            .setTitle(R.string.are_you_sure)
            .setMessage(String.format(getString(R.string.restart_all_points_to_y), activityViewModel.liveInitialPoints.value))
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ -> activityViewModel.restoreOneGamePoints(viewModel.gamePlayersNum!!) }
            .create()
            .show()
    }

    //INNER CLASSES
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

}