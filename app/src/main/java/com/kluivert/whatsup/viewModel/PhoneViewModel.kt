package com.kluivert.whatsup.viewModel

import android.view.View
import androidx.lifecycle.ViewModel

class PhoneViewModel : ViewModel() {

    var phnumber : String? = null

    fun onRegisterNum(view : View){

        if (phnumber.isNullOrEmpty()){


            return
        }

    }


}