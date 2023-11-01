package com.example.viewmodeldemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

/**
 * Criando ViewModel e controlando o estado de forma separada da Activity que sera usada apenas
 * para exibir elementos composable separando a logica da view
 */

class DemoViewModel: ViewModel() {
    // criando estado para saber se esta em fahrenheit ou nao
    var isFahrenheit by mutableStateOf(true)
    // criando estado para exibir o resultado da temperatura
    var result by mutableStateOf("")
    // usando o liveData
    // var isFahrenheit: MutableLiveData<Boolean> = MutableLiveData(true)
    // var result: MutableLiveData<String> = MutableLiveData("")

    // funcao de conversao, onde os resultados que seram retornados sao strings
    fun convert(temp: String) {
         result = try {
            val temperature = temp.toInt()
            if (isFahrenheit) {
                ((temperature - 32) * 0.5556).roundToInt().toString()
            } else {
                ((temperature - 32) * 1.8).roundToInt().toString()
            }
        } catch (_: Exception) {
            "Invalid Entry"
        }
    }

    // funcao que mudara o estado para saber se esta em fahrenheit
    fun switchFahrenheit() {
        isFahrenheit = !isFahrenheit
    }
}