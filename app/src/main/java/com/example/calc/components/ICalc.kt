package com.example.calc.components

import androidx.lifecycle.LiveData

interface ICalc {

    val mode: LiveData<Model>

    fun onClick(button: Button)

    data class Model(val input: String)

    sealed interface Button{
        object Result: Button
        object Reset: Button
        object Minus : Button
        data class Sym(val value: String): Button
    }
}