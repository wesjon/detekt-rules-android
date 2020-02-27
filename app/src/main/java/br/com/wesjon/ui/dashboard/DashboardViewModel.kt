package br.com.wesjon.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class DashboardViewModel : ViewModel() {
    val text: LiveData<String> = liveData {
        emit("This is dashboard Fragment")
    }
}
