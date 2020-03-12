package com.kluivert.whatsup.Views

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
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
import com.kluivert.whatsup.Helper.fonts.QuicksandMedium
import com.kluivert.whatsup.Helper.fonts.QuicksandRegular
import com.kluivert.whatsup.Helper.toast
import com.kluivert.whatsup.R
import java.util.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private var isCancelled = false
    lateinit var edcode : QuickEditText
    lateinit var tvtime : QuicksandRegular
    lateinit var tvresend : QuicksandRegular
    lateinit var receivenum : QuicksandMedium
    lateinit var cardotp : CardView
    lateinit var firebaseAuth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        firebaseAuth = FirebaseAuth.getInstance()

        edcode = findViewById(R.id.edcode)
        cardotp = findViewById(R.id.cardotp)
        tvresend = findViewById(R.id.tvresend)
        tvtime = findViewById(R.id.tvtime)
        receivenum = findViewById(R.id.receivenum)



        var intent = getIntent()
        var strnum = intent.getStringExtra("phone")
        receivenum.text = strnum

        val minute:Long = 1000 * 60
        val millisInFuture:Long = (minute * 0) + (minute * 0) + (1000 * 50)

        val countDownInterval:Long = 1000



            timer(millisInFuture,countDownInterval).start()

           tvresend.isEnabled = false

            isCancelled = false


          tvresend.setOnClickListener {

              resendCode()

          }

      cardotp.setOnClickListener {

          verifyCode()
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


    fun resendCode(){

        verificationcallbacks()

        var strphone: String? = null

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                strphone!!,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks)

    }


    fun verifyCode(){


        val strphone = edcode.text.toString()




    }



    private fun timer(millisInFuture:Long,countDownInterval:Long): CountDownTimer {
        return object: CountDownTimer(millisInFuture,countDownInterval){
            override fun onTick(millisUntilFinished: Long){
                val timeRemaining = timeString(millisUntilFinished)
                if (isCancelled){
                   tvtime.text = "${tvtime.text}\nStopped.(Cancelled)"
                    cancel()
                }else{
                    tvtime.text = timeRemaining
                }
            }

            override fun onFinish() {
               tvtime.text = "Done"

                //button_start.isEnabled = true


            }
        }
    }




    private fun timeString(millisUntilFinished:Long):String{
        var millisUntilFinished:Long = millisUntilFinished
        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        millisUntilFinished -= TimeUnit.DAYS.toMillis(days)

        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d min: %02d sec",
            days,hours, minutes,seconds
        )
    }
}
