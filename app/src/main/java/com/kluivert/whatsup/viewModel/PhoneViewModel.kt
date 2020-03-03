package com.kluivert.whatsup.viewModel

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.kluivert.whatsup.interfaces.AuthListener

class PhoneViewModel : ViewModel() {

    var phnumber : String? = null

    var authListener : AuthListener? = null


    fun onRegisterNum(view : View){

        if (phnumber.isNullOrEmpty()){

            authListener?.onFailure("Please enter phone number")

            return
        }else {
                showdialog()



        }

    }

    fun showdialog() {
         var context : Context? = null
        val alertDialog = AlertDialog.Builder(context!!)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Confirm phone number")
            .setMessage("You are sure about the phone number")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, i ->

                authListener?.onSuccess()


            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            .show()
    }



}