package com.etologic.pointscorer.app.games.base.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.base.BaseMainFragmentWithAds
import com.etologic.pointscorer.app.games.base.player.PlayerFragment

abstract class GameFragment<V : ViewBinding> : BaseMainFragmentWithAds() {

    protected abstract val gamePlayersNum: Int
    private var _binding: V? = null
    protected val binding get() = _binding!!
    private val viewModel: GameViewModel by hiltNavGraphViewModels(R.id.a_main_nav_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateView(inflater, container, savedInstanceState)
        baseBinding = binding
        return binding.root
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayers()
        initViewModel()
        initSpecificViews()
    }

    abstract fun initPlayers()

    protected fun initPlayerFragment(
        playerId: Int,
        @IdRes fragmentContainerViewId: Int,
        playerFragmentInitialData: PlayerFragment.PlayerFragmentInitialData
    ) {
        val playerFragment = PlayerFragment.newInstance(playerId, playerFragmentInitialData)
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .add(fragmentContainerViewId, playerFragment)
            .commit()
    }

    abstract fun initSpecificViews()

    private fun initViewModel() {
        viewModel.gamePlayersNum = gamePlayersNum

        viewModel.showAdWillBeShownDialogObservable()
            .observe(viewLifecycleOwner) { showAdWillBeShownDialogObserver(it) }
        viewModel.showPlayerSettingsMenuRequestedObservable()
            .observe(viewLifecycleOwner) { showPlayerSettingsMenuRequestedObserver(it) }
    }

    private fun showAdWillBeShownDialogObserver(shouldShow: Boolean?) {
        if (shouldShow == true) {
            findNavController().navigate(R.id.adWillBeShownDialogFragment)
        }
    }

    private fun showPlayerSettingsMenuRequestedObserver(playerSettingsMenuInitialData: GameViewModel.PlayerSettingsMenuInitialData?) {
        playerSettingsMenuInitialData?.let {
            findNavController().navigate(getPlayerSettingsMenuNavAction(playerSettingsMenuInitialData))
        }
    }

    abstract fun getPlayerSettingsMenuNavAction(playerSettingsMenuInitialData: GameViewModel.PlayerSettingsMenuInitialData): NavDirections

    fun askConfirmRestoreAllPlayersPoints() {
        viewModel.shouldShowGameInterstitialAd = false
        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
            .setTitle(R.string.are_you_sure)
            .setMessage(getString(R.string.restore_game_points_confirmation, viewModel.initialPoints))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ -> viewModel.restoreGamePoints() }
            .setOnDismissListener { viewModel.shouldShowGameInterstitialAd = true }
            .create()
            .show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onFragmentResumed()
    }

    override fun onPause() {
        viewModel.onFragmentPaused()
        super.onPause()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
