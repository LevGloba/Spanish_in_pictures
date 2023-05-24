package com.example.spanish.domain

import com.example.spanish.di.TakeRulse
import com.example.spanish.di.model.Requests
import javax.inject.Inject

class TakeRulseImpl @Inject constructor(): TakeRulse {

    private var rulse = Requests.FAUNA

    override fun getRulse(): Requests = rulse
    override fun setRulse(v: Requests) {
        rulse = v
    }
}