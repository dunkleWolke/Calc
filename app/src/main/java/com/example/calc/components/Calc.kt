package com.example.calc.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Calc : ICalc {

    private val _mode = MutableLiveData<ICalc.Model>()

    private var currentInput = ""
    private var error = false
    private val values = mutableListOf<Double>()
    private val operations = mutableListOf<String>()

    init {
        _mode.value = ICalc.Model(input = "")
    }

    override val mode: LiveData<ICalc.Model>
        get() = _mode

    override fun onClick(button: ICalc.Button) {
        if (error) {
            _mode.value = ICalc.Model(input = "")
            reset()
            error = false
        }

        when (button) {
            ICalc.Button.Reset -> {
                _mode.value = ICalc.Model(input = "")
                reset()
            }
            ICalc.Button.Result -> {
                if (currentInput.isNotEmpty()) {
                    values.add(currentInput.toDouble())
                }
                val result = evaluate()
                if (result is String) {
                    _mode.value = ICalc.Model(input = result)
                    error = true
                } else {
                    _mode.value = ICalc.Model(input = result.toString())
                }
                reset()
            }
            is ICalc.Button.Sym -> {
                when {
                    button.value == "%" -> {
                        if (currentInput.isNotEmpty()) {
                            val currentValue = currentInput.toDoubleOrNull() ?: 0.0
                            if (values.isNotEmpty()) {
                                val lastValue = values.last()
                                currentInput = (lastValue * currentValue / 100).toString()
                            } else {
                                currentInput = (currentValue / 100).toString()
                            }
                        }
                    }
                    button.value == "." -> {
                        if (!currentInput.contains(".")) {
                            currentInput += "."
                        }
                    }
                    button.value in listOf("+", "-", "*", "/") -> {
                        if (currentInput.isNotEmpty() || values.isNotEmpty()) {
                            if (currentInput.isNotEmpty()) {
                                values.add(currentInput.toDouble())
                                while (operations.isNotEmpty() && hasPrecedence(
                                        button.value,
                                        operations.last()
                                    )
                                ) {
                                    val operationResult = applyOp(
                                        operations.removeAt(operations.size - 1),
                                        values.removeAt(values.size - 1),
                                        values.removeAt(values.size - 1)
                                    )
                                    if (operationResult is String) {
                                        _mode.value = ICalc.Model(input = operationResult)
                                        error = true
                                        return
                                    } else {
                                        values.add(operationResult as Double)
                                    }
                                }
                                operations.add(button.value)
                                currentInput = ""
                            } else if (operations.isNotEmpty()) {
                                operations[operations.size - 1] = button.value
                            } else {
                                operations.add(button.value)
                            }
                        }
                    }
                    else -> {
                        currentInput += button.value
                    }
                }
                _mode.value = ICalc.Model(input = buildInput())
            }
            ICalc.Button.Minus -> {
                setMinus()
            }
        }
    }

    private fun evaluate(): Any {
        while (operations.isNotEmpty()) {
            val operationResult = applyOp(operations.removeAt(operations.size - 1), values.removeAt(values.size - 1), values.removeAt(values.size - 1))
            if (operationResult is String) {
                return operationResult
            } else {
                values.add(operationResult as Double)
            }
        }
        return values.last()
    }

    private fun applyOp(op: String, b: Double, a: Double): Any {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0) a / b else "нельзя"
            else -> 0.0
        }
    }

    private fun hasPrecedence(op1: String, op2: String): Boolean {
        return if ((op1 == "*" || op1 == "/") && (op2 == "+" || op2 == "-")) {
            false
        } else {
            true
        }
    }

    private fun setMinus() {
        val currentInputValue = currentInput.toDoubleOrNull()
        if (currentInputValue != null) {
            currentInput = (-currentInputValue).toString()
        } else if (values.isNotEmpty()) {
            val lastValue = values.removeAt(values.size - 1)
            values.add(-lastValue)
        }
        _mode.value = ICalc.Model(input = buildInput())
    }

    private fun buildInput(): String {
        return buildString {
            values.forEachIndexed { index, value ->
                append(value.toString().removeSuffix(".0"))
                if (index < operations.size) {
                    append(operations[index])
                }
            }
            append(currentInput)
        }
    }

    private fun reset() {
        currentInput = ""
        values.clear()
        operations.clear()
    }
}







