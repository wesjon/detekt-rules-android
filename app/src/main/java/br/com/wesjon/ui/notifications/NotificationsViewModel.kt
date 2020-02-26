package br.com.wesjon.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class NotificationsViewModel : ViewModel() {
    val text: LiveData<String> = liveData {
        emit("This is notifications Fragment")
    }
}