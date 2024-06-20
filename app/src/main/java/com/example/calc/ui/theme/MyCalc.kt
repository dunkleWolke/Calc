package com.example.calc.ui.theme

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calc.components.Calc
import com.example.calc.components.ICalc

@Preview
@Composable
fun ShowMyCalc(){
    MyCalc(Calc())
}


@Composable
fun MyCalc(component: ICalc) {

    val mode by component.mode.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalcInput(input = mode?.input ?: "")
            CalcButtonContainer(onButtonClicked = component::onClick)
        }
    }
}

@Composable
fun CalcInput(
    input: String,
) {
    Text(
        text = input,
        maxLines = 1,
        fontSize = 74.sp,
        color = ResultFont,
        textAlign = TextAlign.End,
        modifier = Modifier
            .padding(6.dp)
            .width(80.dp * 4 + 4.dp * 6)
            .horizontalScroll(rememberScrollState())
    )
}



@Composable
fun CalcButtonContainer(onButtonClicked: (ICalc.Button) -> Unit){
    Column(
        modifier = Modifier
            .padding(bottom = 24.dp)
    ) {
        CalcButtonRow{
            CalcSpecialButton(text = "C", onClick = {onButtonClicked(ICalc.Button.Reset)})
            CalcNumButton(text = "+/-", onClick = {onButtonClicked(ICalc.Button.Minus)})
            CalcOperationButton(text = "%", onClick = {onButtonClicked(ICalc.Button.Sym("%"))})
            CalcOperationButton(text = "/", onClick = {onButtonClicked(ICalc.Button.Sym("/"))})
        }
        CalcButtonRow{
            CalcNumButton(text = "7", onClick = {onButtonClicked(ICalc.Button.Sym("7"))})
            CalcNumButton(text = "8", onClick = {onButtonClicked(ICalc.Button.Sym("8"))})
            CalcNumButton(text = "9", onClick = {onButtonClicked(ICalc.Button.Sym("9"))})
            CalcOperationButton(text = "*", onClick = {onButtonClicked(ICalc.Button.Sym("*"))})
        }
        CalcButtonRow{
            CalcNumButton(text = "4", onClick = {onButtonClicked(ICalc.Button.Sym("4"))})
            CalcNumButton(text = "5", onClick = {onButtonClicked(ICalc.Button.Sym("5"))})
            CalcNumButton(text = "6", onClick = {onButtonClicked(ICalc.Button.Sym("6"))})
            CalcOperationButton(text = "-", onClick = {onButtonClicked(ICalc.Button.Sym("-"))})
        }
        CalcButtonRow{
            CalcNumButton(text = "1", onClick = {onButtonClicked(ICalc.Button.Sym("1"))})
            CalcNumButton(text = "2", onClick = {onButtonClicked(ICalc.Button.Sym("2"))})
            CalcNumButton(text = "3", onClick = {onButtonClicked(ICalc.Button.Sym("3"))})
            CalcOperationButton(text = "+", onClick = {onButtonClicked(ICalc.Button.Sym("+"))})
        }
        CalcButtonRow{
            CalcNumButton(text = "0", onClick = {onButtonClicked(ICalc.Button.Sym("0"))})
            CalcNumButton(text = ".", onClick = {onButtonClicked(ICalc.Button.Sym("."))})
            CalcResultButton(text = "=", onClick = {onButtonClicked(ICalc.Button.Result)}, width = 180.dp)

        }

    }
}

@Composable
fun CalcButtonRow(
    content: @Composable () -> Unit
) {
    Row() {
        content()
    }
}

@Composable
fun CalcButton(
    text: String,
    onClick: () -> Unit,
    color: Color,
    fontColor: Color,
    width: Dp = 90.dp,
    height: Dp = 90.dp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width, height)
            .padding(6.dp)
            .background(color = color, shape = RoundedCornerShape(9.dp))
            .clickable(onClick = onClick)
    ){
        Text(
            text = text,
            color = fontColor,
            fontSize = 36.sp,
        )
    }
}

@Composable
fun CalcOperationButton(
    text: String,
    onClick: () -> Unit,

) {
    CalcButton(
        text = text,
        onClick = onClick,
        color = DefaultButtonBack,
        fontColor = OperationFont,

    )
}

@Composable
fun CalcNumButton(
    text: String,
    onClick: () -> Unit,

) {
    CalcButton(
        text = text,
        onClick = onClick,
        color = DefaultButtonBack,
        fontColor = DefaultFont,

    )
}

@Composable
fun CalcSpecialButton(
    text: String,
    onClick: () -> Unit,

) {
    CalcButton(
        text = text,
        onClick = onClick,
        color = ResetButtonBack,
        fontColor = DefaultButtonBack,

    )
}

@Composable
fun CalcResultButton(
    text: String,
    onClick: () -> Unit,
    width: Dp
) {
    CalcButton(
        text = text,
        onClick = onClick,
        color = OperationFont,
        fontColor = DefaultButtonBack,
        width = width
    )
}

