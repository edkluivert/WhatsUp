package com.kluivert.whatsup.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(context: Context = applicationContext, message: String, toastDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, toastDuration).show()
}