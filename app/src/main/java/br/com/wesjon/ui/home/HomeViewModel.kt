package br.com.wesjon.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class HomeViewModel : ViewModel() {

    val text: LiveData<String> = liveData {
        emit("This is home Fragment")
    }
}