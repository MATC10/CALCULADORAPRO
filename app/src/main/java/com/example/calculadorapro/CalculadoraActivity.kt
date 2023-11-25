package com.example.calculadorapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class CalculadoraActivity : AppCompatActivity() {
    private var operationHistory : String = ""
    private var actualNumber : String = "0"
    private var result : String = ""
    private var calculation: Number = 0   // Pongo la clase Number porque de ahí heredan las clases numéricas de Kotlin y podemos manejarlo tanto con decimales como sin ellos


    private lateinit var tvHistory : TextView
    private lateinit var tvInsertNumber : TextView
    private lateinit var btnCe : Button
    private lateinit var btnC : Button
    private lateinit var iBtnRoot : ImageButton
    private lateinit var iBtnPower : ImageButton
    private lateinit var iBtnEquals : ImageButton
    private lateinit var btn1 : Button
    private lateinit var btn2 : Button
    private lateinit var btn3 : Button
    private lateinit var btn4 : Button
    private lateinit var btn5 : Button
    private lateinit var btn6 : Button
    private lateinit var btn7 : Button
    private lateinit var btn8 : Button
    private lateinit var btn9 : Button
    private lateinit var btn0 : Button
    private lateinit var btnPoint : Button
    private lateinit var btnChangeSign : Button
    private lateinit var btnPlus : Button
    private lateinit var btnMinus : Button
    private lateinit var btnMultiplication : Button
    private lateinit var btnDivision : Button


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
        btnCe = findViewById(R.id.btnCe)
        btnC = findViewById(R.id.btnC)
        iBtnRoot = findViewById(R.id.iBtnRoot)
        iBtnPower = findViewById(R.id.iBtnPower)
        iBtnEquals = findViewById(R.id.iBtnEquals)
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
        btnCe.setOnClickListener {

        }
        tvInsertNumber.setOnClickListener {
            //puedo poner aquí un easter egg, si lo clickeas diez veces sale el nombre de LuisRe
        }
        btnCe.setOnClickListener {
            actualNumber = ""
            insertChanger()
            caseOfError()
        }
        btnC.setOnClickListener {
            clear()
            caseOfError()
        }
        iBtnRoot.setOnClickListener {
            calculateRoot()
        }
        iBtnPower.setOnClickListener {
            powerAdjustment()
        }
        iBtnEquals.setOnClickListener {}
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
        if(actualNumber == "0" && operationHistory == "") { //Al principio esta variable estará vacía aunque en pantalla muestre 0
            tvInsertNumber.text = actualNumber
            actualNumber = ""
        }
    }

    private fun insertChanger(){
        if (actualNumber.isEmpty()) tvInsertNumber.text = "0"
        else tvInsertNumber.text = actualNumber
    }

    private fun toHistoryCalculation(operator: String){
        nonRepeatedOperators(operator)
        actualNumber = ""
        tvHistory.text = operationHistory
        insertChanger()
    }

    private fun specialInsertChanger(){ //actualNumber NUNCA ESTARÁ EN "" EN PANTALLA
        if(actualNumber.contains("√")){
            actualNumber = "ERROR"
            caseOfError()
        } //si actualNumber contiene "√" y ponemos en negativo, da error. Esto identifica los elementos después del "√": actualNumber.substring(actualNumber.indexOf("√") + 1)
        else if(actualNumber.isNotEmpty() && actualNumber.toInt() > 0) { //comprobamos que no esté vacío actual number
            actualNumber = "-${actualNumber}"
        }
        else if(actualNumber.isNotEmpty() && actualNumber.toInt() < 0){
            actualNumber = actualNumber.substring(1) //quita el primer dígito, en este caso el "-"
        }
        tvInsertNumber.text = actualNumber
    }

    private fun calculateRoot(){
        if(actualNumber.isEmpty()) actualNumber += "0"
        if(actualNumber.last().toString() == "√") actualNumber += "√" //se pueden añadir varias raíces de forma anidada
        else if(actualNumber.length == 1 &&  actualNumber.last().toString() == "0") actualNumber = "√" //podemos empezar añadiendo varias raíces anidadas
        else if(actualNumber.isNotEmpty()){ //cuando hay un número antes que una raíz se multiplica por la raíz
            toHistoryCalculation("*")
            actualNumber = "√"
        }
        insertChanger()
    }

    private fun clear(){
        actualNumber = ""
        operationHistory = ""
        insertChanger()
        tvHistory.text = operationHistory
    }

    private fun nonRepeatedOperators(operator: String){
        if(actualNumber.isEmpty() && operationHistory.isEmpty()) {
            actualNumber = "0"
            operationHistory = "0${operator}"
        }else if (operationHistory.length >= 2 && (
            operationHistory.last().toString() == "+" ||
            operationHistory.last().toString() == "-" ||
            operationHistory.last().toString() == "/" ||
            operationHistory.last().toString() == "*" ||
            operationHistory.last().toString() == "^") &&
            operationHistory.last().toString() != operator &&
            actualNumber.isEmpty()) {

            operationHistory = "${operationHistory.dropLast(1)}${operator}"

        } else{
            operationHistory += "${actualNumber}${operator}"
            if(operationHistory.length >= 2 && (operationHistory.last().toString() ==
                operationHistory[operationHistory.length - 2].toString())){
                operationHistory = operationHistory.dropLast(1)
            }
        }
    }


    private fun caseOfError() {
        val allButtons = listOf(
            iBtnRoot, iBtnPower, iBtnEquals, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0,
            btnChangeSign, btnPlus, btnMinus, btnMultiplication, btnDivision, btnPoint
        )

        val disableButtons = actualNumber.contains("ERROR")

        for (button in allButtons) { //si no contiene "ERROR" se habilitan
            button.isEnabled = !disableButtons
        }
    }

    private fun pointsEnabled(){
        if(!actualNumber.contains(".")){
            if(actualNumber.isEmpty()) actualNumber += "0."
            else actualNumber += "."
        }
        insertChanger()
    }

    private fun powerAdjustment(){
        if(!actualNumber.contains("^")){
            if(actualNumber.isEmpty()) actualNumber += "0^"
            else actualNumber += "^"
        }

        toHistoryCalculation("^")
    }



}