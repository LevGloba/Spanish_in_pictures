package com.example.spanish.presentation.choose_theme

import androidx.lifecycle.ViewModel
import com.example.spanish.di.TakeRulse
import com.example.spanish.di.model.Requests
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseThemeViewModel @Inject constructor(private val takeRulse: TakeRulse): ViewModel() {

    fun setRulse(v: Requests) {
        takeRulse.setRulse(v)
    }
}