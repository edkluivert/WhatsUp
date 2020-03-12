package com.kluivert.whatsup.Views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kluivert.whatsup.Helper.ProgressDialogUtil
import com.kluivert.whatsup.Helper.fonts.QuickEditText
import com.kluivert.whatsup.Helper.toast
import com.kluivert.whatsup.R
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import org.intellij.lang.annotations.Language
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class PhoneAuth : AppCompatActivity() {

         lateinit var firebaseAuth: FirebaseAuth
         lateinit var edphone : QuickEditText
        lateinit var cardverify : CardView
        lateinit var progbar : MaterialProgressBar


    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    companion object {
        private const val CREDENTIAL_PICKER_REQUEST = 1
        private const val SMS_CONSENT_REQUEST = 2

        fun getIntent(context: Context): Intent {
            return Intent(context, PhoneAuth::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.useAppLanguage()

        edphone = findViewById(R.id.edphone)
        cardverify = findViewById(R.id.cardverify)
        progbar = findViewById(R.id.progbar)


           cardverify.setOnClickListener {
               verifyNum()
           }




    }

    private fun requestHint() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val credentialsClient = Credentials.getClient(this)
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        startIntentSenderForResult(
            intent.intentSender,
            CREDENTIAL_PICKER_REQUEST,
            null, 0, 0, 0
        )
    }

    private fun startSmsListener(){
        val client = SmsRetriever.getClient(this )
        val task = client.startSmsRetriever()


        task.addOnSuccessListener {


            //otp_txt.text = "Waiting for the OTP"
        }

        task.addOnFailureListener {

           // otp_txt.text = "Cannot Start SMS Retriever"
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CREDENTIAL_PICKER_REQUEST ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                   // viewModel.selectedPhoneNumber.value = credential?.id
                }

            SMS_CONSENT_REQUEST ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    val oneTimeCode = message?.substring(0, 6)
                    Timber.d("AuthActivity.onActivityResult message $oneTimeCode")
                //    viewModel.selectedOtpNumber.value = oneTimeCode?.trim()
                }
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
                  progbar.visibility = View.INVISIBLE
                 goTp()

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

           val dialog = ProgressDialogUtil.setProgressDialog(this, "Loading...")
            dialog.show()
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                strphone,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks)




        }



    }

    fun goTp(){

        var intent = Intent(applicationContext, OtpActivity::class.java)
        intent.putExtra("phone",edphone.text.toString())
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        Animatoo.animateSlideLeft(this)
        finish()
    }

}
