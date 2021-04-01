package com.etologic.pointscorer.app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel
@Inject internal constructor(
) : ViewModel() {
    
    private val _snackbarMessage = MutableLiveData<String>()
    internal fun getSnackbarMessage(): LiveData<String> = _snackbarMessage
    
    internal fun showSnackbar(message: String) {
        _snackbarMessage.postValue(message)
    }
}
