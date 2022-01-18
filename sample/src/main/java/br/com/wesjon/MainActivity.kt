package br.com.wesjon

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main)

open class ViewModel
open class BaseViewModel
open class CustomViewModel
open class MutableLiveData

class LoginViewModel : CustomViewModel() {
    val loginState = MutableLiveData()
}