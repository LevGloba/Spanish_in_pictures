package com.example.spanish.di

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Patterns
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
object SingltonObject {
    val auth = Firebase.auth
    val db = Firebase.firestore
    fun signOut()  {
        auth.signOut()
    }
}

fun String.isEmailValid(): Boolean {
    return  !TextUtils.isEmpty(this) &&
            Patterns.EMAIL_ADDRESS.matcher(this).matches()
}