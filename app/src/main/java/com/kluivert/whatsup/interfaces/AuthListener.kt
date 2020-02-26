package com.kluivert.whatsup.interfaces

interface AuthListener {

 fun onStarted()
 fun onSuccess()
 fun onFailure(message : String)

}