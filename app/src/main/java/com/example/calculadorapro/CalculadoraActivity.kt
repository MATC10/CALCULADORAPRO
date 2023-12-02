package com.example.calculadorapro

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.sqrt


class CalculadoraActivity : AppCompatActivity() {
    private var operationHistory: String = ""
    private var actualNumber: String = "0"
    val numericList = mutableListOf<String>()
    val operatorList = mutableListOf<String>()

    private var operationHistoryInstant: String = ""
    private var actualNumberInstant: String = "0"
    var numericListInstant = mutableListOf<String>()
    var operatorListInstant = mutableListOf<String>()

    private var luisReEasterEgg: Int = 0

    private lateinit var tvHistory: TextView
    private lateinit var tvInsertNumber: TextView
    private lateinit var tvInstantResult: TextView
    private lateinit var btnCe: Button
    private lateinit var btnC: Button
    private lateinit var iBtnRoot: ImageButton
    private lateinit var iBtnPower: ImageButton
    private lateinit var btnEquals: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btn0: Button
    private lateinit var btnPoint: Button
    private lateinit var btnChangeSign: Button
    private lateinit var btnPlus: Button
    private lateinit var btnMinus: Button
    private lateinit var btnMultiplication: Button
    private lateinit var btnDivision: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        tvHistory = findViewById(R.id.tvHistory)
        tvInsertNumber = findViewById(R.id.tvInsertNumber)
        tvInstantResult = findViewById(R.id.tvInstantResult)
        btnCe = findViewById(R.id.btnCe)
        btnC = findViewById(R.id.btnC)
        iBtnRoot = findViewById(R.id.iBtnRoot)
        iBtnPower = findViewById(R.id.iBtnPower)
        btnEquals = findViewById(R.id.btnEquals)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btn0 = findViewById(R.id.btn0)
        btnPoint = findViewById(R.id.btnPoint)
        btnChangeSign = findViewById(R.id.btnChangeSign)
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        btnMultiplication = findViewById(R.id.btnMultiplication)
        btnDivision = findViewById(R.id.btnDivision)
    }

    private fun initListeners() {
        tvHistory.setOnClickListener {
            //EASTER EGG: si lo clickeas diez veces sale el nombre de LuisRe
            easterEggLuisRe()
        }
        tvInsertNumber.setOnClickListener {
            easterEggLuisRe()
        }
        tvInstantResult.setOnClickListener {
            easterEggLuisRe()
        }

        btnCe.setOnClickListener {
            softResetCe()
        }
        btnC.setOnClickListener {
            clear()
            caseOfError()
        }
        iBtnRoot.setOnClickListener {
            rootCalculation()
        }
        iBtnPower.setOnClickListener {
            powerAdjustment()
        }
        btnEquals.setOnClickListener {
            calculateEquals()
        }
        btn1.setOnClickListener {
            actualNumber += "1"
            insertChanger()
        }
        btn2.setOnClickListener {
            actualNumber += "2"
            insertChanger()
        }
        btn3.setOnClickListener {
            actualNumber += "3"
            insertChanger()
        }
        btn4.setOnClickListener {
            actualNumber += "4"
            insertChanger()
        }
        btn5.setOnClickListener {
            actualNumber += "5"
            insertChanger()
        }
        btn6.setOnClickListener {
            actualNumber += "6"
            insertChanger()
        }
        btn7.setOnClickListener {
            actualNumber += "7"
            insertChanger()
        }
        btn8.setOnClickListener {
            actualNumber += "8"
            insertChanger()
        }
        btn9.setOnClickListener {
            actualNumber += "9"
            insertChanger()
        }
        btn0.setOnClickListener {
            actualNumber += "0"
            insertChanger()
        }
        btnPoint.setOnClickListener {
            pointsEnabled()
        }
        btnChangeSign.setOnClickListener {
            specialInsertChanger()
        }
        btnPlus.setOnClickListener {
            toHistoryCalculation("+")
        }
        btnMinus.setOnClickListener {
            toHistoryCalculation("-")
        }
        btnMultiplication.setOnClickListener {
            toHistoryCalculation("*")
        }
        btnDivision.setOnClickListener {
            toHistoryCalculation("/")
        }
    }

    private fun initUI() {
        if (actualNumber == "0" && operationHistory == "") { //Al principio esta variable estará vacía aunque en pantalla muestre 0
            tvInsertNumber.text = actualNumber
            actualNumber = ""
        }
    }

    private fun insertChanger() {
        if (actualNumber.isEmpty()) tvInsertNumber.text = "0"
        else tvInsertNumber.text = actualNumber

        calculateEquals2()
    }

    private fun softResetCe(){
        if(tvHistory.text.isNotEmpty() && tvHistory.text.contains("√-")){
            clear()
            tvHistory.text = ""
            tvInsertNumber.text = "0"
            tvInstantResult.text = "0"
            caseOfError("ERROR")
        }else{
            actualNumber = ""
            insertChanger()
        }
    }
    private fun toHistoryCalculation(operator: String) {
        nonRepeatedOperators(operator)
        actualNumber = ""
        tvHistory.text = operationHistory
        insertChanger()
    }

    private fun specialInsertChanger() { //actualNumber NUNCA ESTARÁ EN "" EN PANTALLA
        if (actualNumber.contains("√")) {
            if(valuesAfterRoot() == "0" || valuesAfterRoot() == "0.0"){
                //no hacer nada
            }
            else{
                operationHistory += "√-${valuesAfterRoot()}"
                tvHistory.text = operationHistory
                actualNumber = "Syntax ERROR"
                tvInstantResult.text = "Syntax ERROR"
                caseOfError()
            }

        } //si actualNumber contiene "√" y ponemos en negativo, da error. Esto identifica los elementos después del "√": actualNumber.substring(actualNumber.indexOf("√") + 1)
        else if (actualNumber.isNotEmpty() && actualNumber.toDouble() > 0) { //comprobamos que no esté vacío actual number
            actualNumber = "-${actualNumber}"
        } else if (actualNumber.isNotEmpty() && actualNumber.toDouble() < 0) {
            actualNumber = actualNumber.substring(1) //quita el primer dígito, en este caso el "-"
        }
        tvInsertNumber.text = actualNumber
    }

    private fun valuesAfterRoot(): String{
        val index = actualNumber.indexOf("√")
        if (index != -1) {
            val afterRoot = actualNumber.substring(index + 1)
            return afterRoot
        }
        return ""
    }

    private fun rootCalculation() {
        if (actualNumber.isEmpty()) actualNumber += "0"
        if (actualNumber.last().toString() == ".") actualNumber += "0"
        if (actualNumber.last()
                .toString() == "√"
        ) caseOfError()
        else if (actualNumber.length == 1 && actualNumber.last().toString() == "0") actualNumber =
            "√"
        else if (actualNumber.isNotEmpty()) { //cuando hay un número antes que una raíz se multiplica por la raíz
            toHistoryCalculation("*")
            actualNumber = "√"
        }
        insertChanger()
    }

    private fun clear() {
        actualNumber = ""
        operationHistory = ""
        insertChanger()
        tvHistory.text = operationHistory
    }

    private fun nonRepeatedOperators(operator: String) {
        if (actualNumber.isNotEmpty() && actualNumber.last().toString() == "√" && operator == "-") {
            actualNumber += "0-"
        }
        if (actualNumber.isNotEmpty() && actualNumber.last()
                .toString() == "."
        ) actualNumber += "0" //si hay un punto antes del operador, habrá un cero entre el punto y el operador

        if (actualNumber.isEmpty() && operationHistory.isEmpty()) {
            actualNumber = "0"
            operationHistory = "0${operator}"
        } else if (operationHistory.length >= 2 && (
                    operationHistory.last().toString() == "+" ||
                            operationHistory.last().toString() == "-" ||
                            operationHistory.last().toString() == "/" ||
                            operationHistory.last().toString() == "*" ||
                            operationHistory.last().toString() == "^") &&
            operationHistory.last().toString() != operator &&
            actualNumber.isEmpty()
        ) {

            operationHistory = "${operationHistory.dropLast(1)}${operator}"
        } else {
            operationHistory += "${actualNumber}${operator}"
            if (operationHistory.length >= 2 && (operationHistory.last().toString() ==
                        operationHistory[operationHistory.length - 2].toString())
            ) {
                operationHistory = operationHistory.dropLast(1)
            }
        }
    }


    private fun caseOfError(StringError: String = "ERROR") {
        val allButtons = listOf(
            iBtnRoot,
            iBtnPower,
            btnEquals,
            btn1,
            btn2,
            btn3,
            btn4,
            btn5,
            btn6,
            btn7,
            btn8,
            btn9,
            btn0,
            btnChangeSign,
            btnPlus,
            btnMinus,
            btnMultiplication,
            btnDivision,
            btnPoint
        )

        val disableButtons = actualNumber.contains("ERROR")
        if (StringError == "ERROR") {
            operationHistory = ""
        }
        for (button in allButtons) { //si no contiene "ERROR" se habilitan
            button.isEnabled = !disableButtons
        }
    }

    private fun pointsEnabled() {
        if (!actualNumber.contains(".")) {
            if (actualNumber.isEmpty()) actualNumber += "0."
            else if (actualNumber.last().toString() == "√") actualNumber += "0."
            else actualNumber += "."
        }
        insertChanger()
    }

    private fun equalsCalculation() {
        if (actualNumber.isNotEmpty()) {
            operationHistory += actualNumber
        }
        if (operationHistory.isNotEmpty() && operationHistory.last()
                .toString() in listOf<String>("+", "-", "*", "/", "^")
        ) {
            operationHistory = operationHistory.dropLast(1)
        } else if (operationHistory.isNotEmpty() && operationHistory.last().toString() == "√") {
            operationHistory += "0"
        }
    }

    private fun powerAdjustment() {
        if (actualNumber.isEmpty()) actualNumber += "0"
        if (!actualNumber.contains("^")) {
            if (actualNumber.last().toString() == ".") actualNumber += "0"
            else if (actualNumber.last().toString() == "√") actualNumber += "0."
            else if (actualNumber.last().toString() == "^") actualNumber += "0."
            else if (actualNumber.isEmpty() || actualNumber.length > 0 && actualNumber.last()
                    .toString() == "√"
            ) actualNumber += "0^"
            // else if(actualNumber.length > 0 && actualNumber.last().toString() == "√")  actualNumber += "0^"
            else actualNumber += "^"
        }

        toHistoryCalculation("^")
    }

    private fun calculate() {
        //cada elemento de cada valor de la lista meterlo en otra lista
        var number = ""
        for (i in operationHistory.indices) {
            val c = operationHistory[i]
            if (c in '0'..'9' || c == '.' || c == '√' || (c == '-' && number.isNotEmpty() && number.last() == '√')) {
                number += c
            } else if (c == '-' && (i == 0 || operationHistory[i - 1] !in '0'..'9' && operationHistory[i - 1] != '.')) {
                number += c
            } else {
                if (number.isNotEmpty()) {
                    numericList.add(number)
                    number = ""
                }
                operatorList.add(c.toString())
            }
        }
        if (number.isNotEmpty()) {
            numericList.add(number)
        }
    }

    private fun finalCalculations() {
        var result: Double
        var elements: String
        for (i in numericList.indices) {  //calcular el root
            if (numericList[i].contains("√")) {
                elements = numericList[i].substring(1)
                if (elements == "") elements = "0"
                numericList[i] = (sqrt(elements.toDouble())).toString()
            }
        }
        while ("^" in operatorList) {
            for (i in operatorList.indices) { //calcular la potencia
                if (operatorList[i] == "^") {
                    result = numericList[i].toDouble().pow(numericList[i + 1].toDouble())
                    numericList[i] = result.toString()
                    numericList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break
                }
            }
        }
        while ("*" in operatorList || "/" in operatorList) {
            for (i in operatorList.indices) { //calcular la multiplicación y división
                if (operatorList[i] == "*") {
                    result = numericList[i].toDouble() * numericList[i + 1].toDouble()
                    numericList[i] = result.toString()
                    numericList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break
                } else if (operatorList[i] == "/") {
                    result = numericList[i].toDouble() / numericList[i + 1].toDouble()
                    numericList[i] = result.toString()
                    numericList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break
                }
            }
        }
        while ("+" in operatorList || "-" in operatorList) {
            for (i in operatorList.indices) { //calcular la suma y resta
                if (operatorList[i] == "+") {
                    result = numericList[i].toDouble() + numericList[i + 1].toDouble()
                    numericList[i] = result.toString()
                    numericList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break
                } else if (operatorList[i] == "-") {
                    result = numericList[i].toDouble() - numericList[i + 1].toDouble()
                    numericList[i] = result.toString()
                    numericList.removeAt(i + 1)
                    operatorList.removeAt(i)
                    break
                }
            }
        }

    }

    private fun calculateEquals() {
        var result: String = "0"
        var resultDone: Boolean = false

        if (operationHistory == "" && actualNumber == "") { //manejo que no se marque ningún número en un inicio
            operationHistory = "0"
            result = "0"
            resultDone = true
        } else {
            equalsCalculation()
            calculate()
            finalCalculations()
            result = resultWithoutDouble()
        }

        if (resultDone == false && resultWithoutDouble() == "Infinity") { //manejo el resultado infinito
            tvHistory.text = "$operationHistory="
            tvInsertNumber.text = result
            operationHistory = ""
            actualNumber = ""
        } else {
            tvHistory.text = "$operationHistory="
            tvInsertNumber.text = result
            operationHistory = ""
            actualNumber = result

        }
        numericList.clear()
        operatorList.clear()
    }

    private fun resultWithoutDouble(): String {
        var firstNumber = numericList.first()
        if (firstNumber != null && firstNumber.endsWith(".0")) {
            firstNumber = firstNumber.dropLast(2)
        } else if (firstNumber != null && firstNumber.contains(".")) {
            var decimal = BigDecimal(firstNumber)
            firstNumber = decimal.setScale(3, RoundingMode.HALF_UP).toString()

            if (firstNumber.endsWith("00")) {
                firstNumber = firstNumber.dropLast(2)
            } else if (firstNumber.endsWith("0")) {
                firstNumber = firstNumber.dropLast(1)
            }
        }
        return firstNumber
    }

    private fun easterEggLuisRe() {
        luisReEasterEgg++
        if (luisReEasterEgg == 10) {
            tvHistory.text = "LUISRE MANDA"
            tvInsertNumber.text = "SOBRE TÚ"
            tvInstantResult.text = "Y TU BANDA"

            luisReEasterEgg = 0 // reset the counter
        }
    }

    //============================ INSTANT RESULT=================================
    //Este apartado es para autocalcular cada paso en el tercer text view

    private fun equalsCalculation2() {
        if (actualNumberInstant.isNotEmpty()) {
            operationHistoryInstant += actualNumberInstant
        }
        if (operationHistoryInstant.isNotEmpty() && operationHistoryInstant.last()
                .toString() in listOf<String>("+", "-", "*", "/", "^")
        ) {
            operationHistoryInstant = operationHistoryInstant.dropLast(1)
        } else if (operationHistoryInstant.isNotEmpty() && operationHistoryInstant.last()
                .toString() == "√"
        ) {
            operationHistoryInstant += "0"
        }
    }


    private fun calculate2() {
        //cada elemento de cada valor de la lista meterlo en otra lista
        var number = ""
        for (i in operationHistoryInstant.indices) {
            val c = operationHistoryInstant[i]
            if (c in '0'..'9' || c == '.' || c == '√' || (c == '-' && number.isNotEmpty() && number.last() == '√')) {
                number += c
            } else if (c == '-' && (i == 0 || operationHistoryInstant[i - 1] !in '0'..'9' && operationHistoryInstant[i - 1] != '.')) {
                number += c
            } else {
                if (number.isNotEmpty()) {
                    numericListInstant.add(number)
                    number = ""
                }
                operatorListInstant.add(c.toString())
            }
        }
        if (number.isNotEmpty()) {
            numericListInstant.add(number)
        }
    }

    private fun finalCalculations2() {
        var result: Double
        var elements: String
        for (i in numericListInstant.indices) {  //calcular el root
            if (numericListInstant[i].contains("√")) {
                elements = numericListInstant[i].substring(1)
                if (elements == "") elements = "0"
                numericListInstant[i] = (sqrt(elements.toDouble())).toString()
            }
        }
        while ("^" in operatorListInstant) {
            for (i in operatorListInstant.indices) { //calcular la potencia
                if (operatorListInstant[i] == "^") {
                    result =
                        numericListInstant[i].toDouble().pow(numericListInstant[i + 1].toDouble())
                    numericListInstant[i] = result.toString()
                    numericListInstant.removeAt(i + 1)
                    operatorListInstant.removeAt(i)
                    break
                }
            }
        }
        while ("*" in operatorListInstant || "/" in operatorListInstant) {
            for (i in operatorListInstant.indices) { //calcular la multiplicación y división
                if (operatorListInstant[i] == "*") {
                    result = numericListInstant[i].toDouble() * numericListInstant[i + 1].toDouble()
                    numericListInstant[i] = result.toString()
                    numericListInstant.removeAt(i + 1)
                    operatorListInstant.removeAt(i)
                    break
                } else if (operatorListInstant[i] == "/") {
                    result = numericListInstant[i].toDouble() / numericListInstant[i + 1].toDouble()
                    numericListInstant[i] = result.toString()
                    numericListInstant.removeAt(i + 1)
                    operatorListInstant.removeAt(i)
                    break
                }
            }
        }
        while ("+" in operatorListInstant || "-" in operatorListInstant) {
            for (i in operatorListInstant.indices) { //calcular la suma y resta
                if (operatorListInstant[i] == "+") {
                    result = numericListInstant[i].toDouble() + numericListInstant[i + 1].toDouble()
                    numericListInstant[i] = result.toString()
                    numericListInstant.removeAt(i + 1)
                    operatorListInstant.removeAt(i)
                    break
                } else if (operatorListInstant[i] == "-") {
                    result = numericListInstant[i].toDouble() - numericListInstant[i + 1].toDouble()
                    numericListInstant[i] = result.toString()
                    numericListInstant.removeAt(i + 1)
                    operatorListInstant.removeAt(i)
                    break
                }
            }
        }

    }

    private fun calculateEquals2() {
        operationHistoryInstant = operationHistory
        actualNumberInstant = actualNumber
        numericListInstant = numericList.toMutableList()
        operatorListInstant = operatorList.toMutableList()


        var result: String = "0"
        var resultDone: Boolean = false

        if (operationHistoryInstant == "" && actualNumberInstant == "") { //manejo que no se marque ningún número en un inicio
            operationHistoryInstant = "0"
            result = "0"
            resultDone = true
        } else {
            equalsCalculation2()
            calculate2()
            finalCalculations2()
            result = resultWithoutDouble2()
        }

        if (resultDone == false && resultWithoutDouble2() == "Infinity") { //manejo el resultado infinito
            tvInstantResult.text = result
        } else {
            tvInstantResult.text = result

        }
        numericListInstant.clear()
        operatorListInstant.clear()
    }

    private fun resultWithoutDouble2(): String {
        var firstNumber = numericListInstant.first()
        if (firstNumber != null && firstNumber.endsWith(".0")) {
            firstNumber = firstNumber.dropLast(2)
        } else if (firstNumber != null && firstNumber.contains(".")) {
            var decimal = BigDecimal(firstNumber)
            firstNumber = decimal.setScale(3, RoundingMode.HALF_UP).toString()

            if (firstNumber.endsWith("00")) {
                firstNumber = firstNumber.dropLast(2)
            } else if (firstNumber.endsWith("0")) {
                firstNumber = firstNumber.dropLast(1)
            }
        }
        return firstNumber
    }

}

