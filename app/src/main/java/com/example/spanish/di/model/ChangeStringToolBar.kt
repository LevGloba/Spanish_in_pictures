package com.example.spanish.di.model

import android.app.Activity

interface ChangeStringToolBar {
    fun changeForTheme(c: Activity)
    fun changeForMode(c: Activity)
    fun setTheme(theme: String)
    fun setMode(mode: String)
    fun getTheme(): String
}