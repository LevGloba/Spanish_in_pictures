package com.example.spanish.presentation.test.mp3

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spanish.R
import com.example.spanish.di.SaveResult
import com.example.spanish.di.TakeDataFromFireStore
import com.example.spanish.di.TakeRulse
import com.example.spanish.di.model.Requests
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TestMP3State {
    data class IdSound(val id: Int) : TestMP3State()
    data class ErrorMessange(val e: String) : TestMP3State()

    data class ErrorAnswer(val e: String) : TestMP3State()

    data class NextStep(val count: Int) : TestMP3State()
    object ClearEditText : TestMP3State()
}

@HiltViewModel
class TestMP3ViewModel @Inject constructor(takeRulse: TakeRulse,private val saveResult: SaveResult): ViewModel() {

    private val _state = MutableSharedFlow<TestMP3State>(onBufferOverflow = BufferOverflow.DROP_OLDEST, replay = 1)
    val state = _state.asSharedFlow()

    private val mapFauna = mapOf(
        "el oso" to R.raw.oso,
        "la cabra montés" to R.raw.cabra_montes,
        "el lobo ibérico" to R.raw.lobo_iberico,
        "el lince ibérico" to R.raw.lince_iberico,
        "la liebre" to R.raw.liebre,
        "araña" to R.raw.arana,
        "tortuga" to R.raw.tortuga,
        "lagarto" to R.raw.lagarto,
        "escorpión" to R.raw.escorpion,
        "serpiente" to R.raw.serpiente,
        "conejo" to R.raw.conejo,
        "gallina" to R.raw.gallina,
        "vaca" to R.raw.vaca,
        "pato" to R.raw.pato,
        "cabra" to R.raw.cabra,
        "toro" to R.raw.toro,
        "abubilla" to R.raw.abubilla,
        "cigüeña" to R.raw.ciguena,
        "gorrión común" to R.raw.gorrion_comun,
        "mirlo común" to R.raw.mirlo_comun
    )

    private val mapFlora = mapOf(
        "el aguacate" to R.raw.aguacate,
        "la aceituna" to R.raw.aceituna,
        "la cereza" to R.raw.cereza,
        "el kaki " to R.raw.kaki_caqui,
        "la manzana" to R.raw.manzana,
        "melocotón" to R.raw.melocoton,
        "pera" to R.raw.pera,
        "uvas" to R.raw.uvas,
        "níspero" to R.raw.nispero,
        "chirimoya" to R.raw.chirimoya,
        "limón" to R.raw.limon,
        "pitaya" to R.raw.pitaya,
        "naranja" to R.raw.naranja,
        "acai" to R.raw.acai,
        "carambola" to R.raw.carambola,
        "guayaba" to R.raw.guayaba,
        "madroño" to R.raw.madrono,
        "higuera" to R.raw.higuera,
        "tamrindo" to R.raw.tamrindo,
        "platano" to R.raw.platano
    )

    private var viewMap: MutableMap<String, Int>? = null
    private var answer: String = ""
    private val answersMap = mutableMapOf<String,String>()

    private var countAnswer = 0
    private var countTask  = 0
    private var taks = 0
    private var modeTesting = true

    fun setCountTaskAndModeTesting(v: Int,b: Boolean) {
        countTask = v
        modeTesting = b
        taks = v
        Log.e("mp3","$modeTesting = $countTask")
    }

    init {
        viewModelScope.launch {
            viewMap = when (takeRulse.getRulse()) {
                Requests.FAUNA ->
                    mapFauna.toMutableMap()

                else ->
                    mapFlora.toMutableMap()
            }
            random()
        }
    }

    private fun random() {
        viewModelScope.launch {
            val size = viewMap?.size?: 1
            val keys = viewMap?.keys?.toList()
            val values = viewMap?.values?.toList()
            val randomInt = (0 until size).random()
            values?.get(randomInt)?.let { TestMP3State.IdSound(it) }?.let { _state.emit(it) }
            answer = keys?.get(randomInt)?: ""
            Log.e("mp3","key = $answer\nvalue = ${values?.get(randomInt)}")
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
                                    _state.emit(TestMP3State.ClearEditText)
                                    countAnswer += 1
                                    countTask -= 1
                                    random()
                                } else {
                                    countAnswer += 1
                                    _state.emit(TestMP3State.NextStep(countAnswer))
                                }
                            }
                            else -> {
                                _state.emit(TestMP3State.ErrorAnswer(answer))
                            }
                        }
                    else
                        _state.emit(
                            TestMP3State.ErrorMessange(
                                "Запрос не удалось выполнить." +
                                        "\nНевозможно проверить ответ, перезайдите на вкладку снова"
                            )
                        )
                }
                false -> {
                    countTask -= 1
                    answersMap[answer] = s.orEmpty()
                    when (countTask) {
                        0 -> {
                            if (s.equals(str))
                                countAnswer += 1
                            saveResult.save(
                                countAnswer = countAnswer,
                                answers = answersMap,
                                countTask = taks,
                                mode = "Аудиофайлы"
                            )
                            _state.emit(TestMP3State.NextStep(countAnswer))
                        }

                        else -> {
                            if (s.equals(answer.filterNot { it.isWhitespace() }.lowercase()))
                                countAnswer += 1
                            _state.emit(TestMP3State.ClearEditText)
                            random()
                        }
                    }
                }
            }
        }
    }


}