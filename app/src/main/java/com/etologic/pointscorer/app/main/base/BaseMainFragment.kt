package com.etologic.pointscorer.app.main.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseMainFragment : Fragment() {

    protected val activityViewModel: MainActivityViewModel by activityViewModels()

}
