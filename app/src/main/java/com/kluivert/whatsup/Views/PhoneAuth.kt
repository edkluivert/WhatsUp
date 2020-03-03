package com.kluivert.whatsup.Views

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.kluivert.whatsup.R
import com.kluivert.whatsup.databinding.ActivityPhoneAuthBinding
import com.kluivert.whatsup.interfaces.AuthListener
import com.kluivert.whatsup.Helper.ProgressDialogUtil
import com.kluivert.whatsup.Helper.toast
import com.kluivert.whatsup.viewModel.PhoneViewModel

class PhoneAuth : AppCompatActivity(), AuthListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binder : ActivityPhoneAuthBinding = DataBindingUtil.setContentView(this,R.layout.activity_phone_auth)

        val viewModelPhone = ViewModelProviders.of(this).get(PhoneViewModel::class.java)

        binder.phoneviewmodel = viewModelPhone

        viewModelPhone.authListener = this

    }

    override fun onStarted() {


    }

    override fun onSuccess() {
       // showdialog()
        val dialog = ProgressDialogUtil.setProgressDialog(this, "Registering phone number..")
        dialog.show()

    }


    override fun onFailure(message: String) {
             toast(this,message,toastDuration = Toast.LENGTH_SHORT)
    }





}
