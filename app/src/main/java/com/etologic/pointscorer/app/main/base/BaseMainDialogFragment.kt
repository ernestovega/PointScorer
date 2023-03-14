package com.etologic.pointscorer.app.main.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseMainDialogFragment : DialogFragment() {

    protected val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onPause() {
        super.onPause()
        dismiss()
    }

}
