package com.example.spanish.di

import com.example.spanish.domain.Result

interface SaveResult {
    fun save(answers: Map<String, String>, countAnswer: Int, countTask: Int)
    fun getResult(): Result?
}