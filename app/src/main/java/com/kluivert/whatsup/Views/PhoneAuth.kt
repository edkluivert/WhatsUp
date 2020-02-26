package com.kluivert.whatsup.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kluivert.whatsup.R
import com.kluivert.whatsup.interfaces.AuthListener

class PhoneAuth : AppCompatActivity(), AuthListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
    }

    override fun onStarted() {



    }

    override fun onSuccess() {


    }

    override fun onFailure(message: String) {

    }
}
