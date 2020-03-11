package com.kluivert.whatsup.Views

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kluivert.whatsup.Helper.fonts.QuickEditText
import com.kluivert.whatsup.Helper.toast
import com.kluivert.whatsup.R
import org.intellij.lang.annotations.Language
import java.util.*
import java.util.concurrent.TimeUnit

class PhoneAuth : AppCompatActivity() {

         lateinit var firebaseAuth: FirebaseAuth
         lateinit var edphone : QuickEditText
        lateinit var cardverify : CardView


    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.useAppLanguage()

        edphone = findViewById(R.id.edphone)
        cardverify = findViewById(R.id.cardverify)

           cardverify.setOnClickListener {
               verifyNum()
           }


    }

    private fun verificationcallbacks(){

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {


            }

            override fun onVerificationFailed(e: FirebaseException) {


                if (e is FirebaseAuthInvalidCredentialsException) {
                    toast(message = e.toString(), toastDuration = Toast.LENGTH_SHORT)

                } else if (e is FirebaseTooManyRequestsException) {
                    toast(message = e.toString(), toastDuration = Toast.LENGTH_SHORT)

                }

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

                storedVerificationId = verificationId
                resendToken = token


            }
        }

    }


    fun verifyNum(){

        verificationcallbacks()

        val strphone = edphone.text.toString()

        if (strphone.isNullOrEmpty()){
            edphone.setError("Please enter your phone number")
            edphone.requestFocus()
            return
        }else{

            var context : Context? = null
            val alertDialog = AlertDialog.Builder(context!!)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm phone number")
                .setMessage("You are sure about the phone number")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, i ->

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        strphone,
                        60,
                        TimeUnit.SECONDS,
                        this,
                        callbacks)



                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()



        }



    }


}
