package com.example.spanish.presentation.test

import android.util.Log
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spanish.R
import com.example.spanish.di.TakeDataFromFireStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

open class Test {
    data class Error(val e: String) : Test()
    data class TestImg(val img: String?) : Test()
    object PlayLoad : Test()
    object StopLoad : Test()
    data class ErrorAnswer(val error: String): Test()
    data class NextStep(val count: Int): Test()
    object ClearEditText: Test()
}

@HiltViewModel
class TestViewModel @Inject constructor(private val takeDataFromFireStore: TakeDataFromFireStore): ViewModel() {


    private val _test = MutableStateFlow(Test())
    val test: StateFlow<Test> = _test.asStateFlow()

    private var viewMap: MutableMap<String, Any>? = null
    private var answer: String = ""

    private var countAnswer = 0
    private var countTask  = 0
    private var modeTesting = true

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _test.emit(Test.PlayLoad)
                val data =  takeDataFromFireStore.takePictcher().await().data?.toMutableMap()
                viewMap = data
                _test.emit(Test.StopLoad)
                random()
                    //Log.e(null, "data = ${data.await()}")
            } catch (e: Throwable) {
                _test.emit(Test.Error(e.message ?: "Error"))
                Log.e(null,e.message?: "Error")
            } finally {
                _test.emit(Test.StopLoad)
            }
        }
    }

    fun setCountTaskAndModeTesting(v: Int, b: Boolean) {
        countTask = v
        modeTesting = b
    }

    private fun random() {
        viewModelScope.launch {
            val size = viewMap?.size?: 1
            val keys = viewMap?.keys?.toList()
            val values = viewMap?.values?.toList()
            val randomInt = (0 until size).random()
            _test.emit(Test.TestImg(values?.get(randomInt).toString()))
            answer = keys?.get(randomInt)?: ""
            Log.e(null,"key = $answer\nvalue = ${values?.get(randomInt)}")
            viewMap?.remove(answer)
        }
    }

    fun check(s: String?) {
        val str = answer.filterNot { it.isWhitespace() }.lowercase()
        viewModelScope.launch {
            when (modeTesting) {
                true -> {
                    if (str.isNotEmpty())
                        when (s) {
                            str -> {
                                if (!viewMap.isNullOrEmpty()) {
                                    _test.emit(Test.ClearEditText)
                                    countAnswer += 1
                                    countTask -= 1
                                    random()
                                } else {
                                    countAnswer += 1
                                    _test.emit(Test.NextStep(countAnswer))
                                }
                            }
                            else -> {
                                _test.emit(Test.ErrorAnswer(answer))
                            }
                        }
                    else
                        _test.emit(Test.Error("Запрос не удалось выполнить." +
                                "\nНевозможно проверить ответ, перезайдите на вкладку снова"))
                }
                false -> {
                    countTask -= 1
                    when (countTask) {
                        0 -> {
                            if (s.equals(str))
                                countAnswer += 1
                            _test.emit(Test.NextStep(countAnswer))
                        }

                        else -> {
                            if (s.equals(answer.filterNot { it.isWhitespace() }.lowercase()))
                                countAnswer += 1
                            _test.emit(Test.ClearEditText)
                            random()
                        }
                    }
                }
            }
        }
    }

}