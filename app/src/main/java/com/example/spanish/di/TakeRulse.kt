package com.example.spanish.di

import com.example.spanish.di.model.Requests

interface TakeRulse {
    fun setRulse(v: Requests)
    fun getRulse(): Requests
}