package com.example.spanish.domain

import android.app.Activity
import com.example.spanish.MainActivity
import com.example.spanish.di.model.ChangeStringToolBar
import javax.inject.Inject

class ChangeStringToolBarImp @Inject constructor(): ChangeStringToolBar {
    private var theme: String = ""
    private var mode: String = ""

    override fun changeForTheme(c: Activity) = (c as MainActivity).changeTextViewLogoTheme(theme)

    override fun changeForMode(c: Activity) = (c as MainActivity).changeTextViewLogoMode(mode)

    override fun setTheme(theme: String) {
        this.theme = theme
    }

    override fun setMode(mode: String) {
        this.mode = mode
    }

    override fun getTheme(): String = theme
}