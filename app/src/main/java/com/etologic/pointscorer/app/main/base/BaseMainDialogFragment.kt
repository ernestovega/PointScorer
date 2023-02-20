package com.etologic.pointscorer.app.main.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel
import com.etologic.pointscorer.app.main.activity.MainActivityViewModelFactory
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

abstract class BaseMainDialogFragment : DaggerDialogFragment() {

    @Inject
    internal lateinit var activityViewModelFactory: MainActivityViewModelFactory
    protected lateinit var activityViewModel: MainActivityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActivityViewModel()
    }

    private fun initActivityViewModel() {
        activity?.let {
            activityViewModel = ViewModelProvider(
                it,
                activityViewModelFactory
            ).get(MainActivityViewModel::class.java)
        }
    }

}
