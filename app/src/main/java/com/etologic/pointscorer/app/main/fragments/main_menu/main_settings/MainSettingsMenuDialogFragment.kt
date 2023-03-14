package com.etologic.pointscorer.app.main.fragments.main_menu.main_settings

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.common.ads.MyRobaAd
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeLoadedException
import com.etologic.pointscorer.app.common.exceptions.AdCouldNotBeShownException
import com.etologic.pointscorer.app.common.utils.ViewExtensions.hideKeyboard
import com.etologic.pointscorer.app.main.base.BaseMainDialogFragment
import com.etologic.pointscorer.common.Constants
import com.etologic.pointscorer.databinding.MainMenuSettingsDialogFragmentBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class MainSettingsMenuDialogFragment
@Inject constructor() : BaseMainDialogFragment() {

    private var _binding: MainMenuSettingsDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainSettingsMenuDialogViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainMenuSettingsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        initAd()
        viewModel.loadInitialPoints()
    }

    private fun initListeners() {

        with(binding) {

            fun showConfirmationDialog(@StringRes title: Int, action: () -> Unit) {
                AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle(title)
                    .setMessage(R.string.are_you_sure)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.ok) { _, _ -> action.invoke() }
                    .create()
                    .show()
            }

            btMainSettingsMenuClose.setOnClickListener { dismiss() }

            tvMainSettingsMenuInitialPointsTitle.setOnClickListener {
                AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle(R.string.initial_points)
                    .setMessage(R.string.initial_points_help)
                    .setPositiveButton(R.string.ok) { _, _ -> etMainSettingsMenuInitialPoints.hideKeyboard() }
                    .create()
                    .show()
            }

            etMainSettingsMenuInitialPoints.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        var points = try {
                            p0.toString().toInt()
                        } catch (nfe: NumberFormatException) {
                            FirebaseCrashlytics.getInstance().recordException(nfe)
                            etMainSettingsMenuInitialPoints.error = getString(R.string.error_initial_points)
                            return
                        }
                        if (points > Constants.MAX_POINTS) {
                            points = Constants.MAX_POINTS
                            etMainSettingsMenuInitialPoints.error = getString(R.string.error_max_points)
                            etMainSettingsMenuInitialPoints.setText(points.toString())
                        } else if (points < Constants.MIN_POINTS) {
                            points = Constants.MIN_POINTS
                            etMainSettingsMenuInitialPoints.error = getString(R.string.error_min_points)
                            etMainSettingsMenuInitialPoints.setText(points.toString())
                        }
                        viewModel.saveInitialPoints(points)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            btMainSettingMenuRestoreAllPoints.setOnClickListener {
                showConfirmationDialog(R.string.restore_all_points) { viewModel.restoreAllPoints() }
            }

            btMainSettingMenuRestoreAllNames.setOnClickListener {
                showConfirmationDialog(R.string.restore_all_names) { viewModel.restoreAllNames() }
            }

            btMainSettingMenuRestoreAllColors.setOnClickListener {
                showConfirmationDialog(R.string.restore_all_colors) { viewModel.restoreAllColors() }
            }

            btMainSettingMenuRestoreAllBackgrounds.setOnClickListener {
                showConfirmationDialog(R.string.restore_all_backgrounds) { viewModel.restoreAllBackgrounds() }
            }

            btMainSettingMenuRestoreEverything.setOnClickListener {
                showConfirmationDialog(R.string.restore_everything) { viewModel.restoreEverything() }
            }
        }
    }

    private fun initObservers() {
        viewModel.initialPointsObservable().observe(viewLifecycleOwner) { initialPointsObserver(it) }
        viewModel.toastStringIdObservable().observe(viewLifecycleOwner) { showToast(it) }
    }

    private fun initialPointsObserver(initialPoints: Int) {
        binding.etMainSettingsMenuInitialPoints.setText(initialPoints.toString())
    }

    private fun showToast(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun initAd() {
        with(MyRobaAd.getNewInstance(BuildConfig.ADMOB_ADUNIT_ROBA, requireContext())) {
            try {
                load(requireContext()) {
                    with(binding.llMainSettingsMenuMediumRectangleAdContainer) {
                        visibility = try {
                            show(this)
                            VISIBLE
                        } catch (e: AdCouldNotBeShownException) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                            GONE
                        }
                    }
                }
            } catch (e: AdCouldNotBeLoadedException) {
                FirebaseCrashlytics.getInstance().recordException(e)
                binding.llMainSettingsMenuMediumRectangleAdContainer.visibility = GONE
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        binding.etMainSettingsMenuInitialPoints.hideKeyboard()
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}