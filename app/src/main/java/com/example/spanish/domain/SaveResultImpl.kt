package com.example.spanish.domain

import com.example.spanish.di.SaveResult
import com.example.spanish.di.TakeRulse
import javax.inject.Inject

class SaveResultImpl @Inject constructor(private val takeRulse: TakeRulse): SaveResult {

    private var result: Result? = null

    override fun getResult(): Result? = result

    override fun save(answers: Map<String, String>, countAnswer: Int, countTask: Int, mode: String) {
        result = Result(answers, countAnswer, countTask, takeRulse.getRulse().theme, mode)
    }
}
data class Result(
    val answers: Map<String, String>,
    val countAnswer: Int,
    val countTask: Int,
    val theme: String,
    val mode: String
)