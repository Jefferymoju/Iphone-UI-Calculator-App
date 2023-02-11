package com.jefferymoju

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // Text view for the result when buttons are clicked
    private var tvResult: TextView? = null
    // create var lastNumeric and lastDot set to boolean to check if last onclick event was a numeral or dot
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.result)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    // Function for the onClick on Digit event.
    fun onDigit(view: View){
        // Append the digit clicked to the previous digit in the textview
        tvResult?.append((view as Button).text)
        //set last onclick numeral to true
        lastNumeric = true
        //set lastDot to false
        lastDot = false
    }

    //function for clearing the textview result
    fun onClear(view: View){
        tvResult?.text = ""
    }

    // onDecimal clicked function
    fun onDecimalPoint(view: View){
        // condition statement to check if the last was a numeral or not
        if (lastNumeric && !lastDot){
            //if lastNumeric is true then append .(add the decimal) else don't
            tvResult?.append(".")
            //set lastNumeric to false
            lastNumeric = false
            // set lastDot to true so as not to add another decimal to it
            lastDot = true
        }
    }

    // onOperator selected function
    fun onOperator(view: View){
        tvResult?.text.let {

            // if the last entry was a numeral and is not operator added
            if (lastNumeric && !onOperatorAdded(it.toString())){
                // append the operator clicked
                tvResult?.append((view as Button).text)
                //set last numeral to false
                lastNumeric = false
                // set lastDot to false
                lastDot = false
            }
        }
    }

    // function for onEqual clicked event
    fun onEqual(view: View){
        //check if last entry was a numeral
        if (lastNumeric){
            var tvValue = tvResult?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                //check if the values in the textview contains the operator -
                if (tvValue.contains("-")){
                    // split the two value with the operator
                    val splitValue = tvValue.split("-")

                    // value one is the first number entered before the operator
                    var one = splitValue[0]
                    // value two at position 1 is the value after the operator
                    var two = splitValue[1]

                    //check if the prefix is empty or not
                    if (prefix.isNotEmpty()){
                        //if it is not empty add the prefix to value one
                        one = prefix + one
                    }

                    tvResult?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                }else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvResult?. text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                }else if(tvValue.contains("÷")){
                    val splitValue = tvValue.split("÷")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvResult?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                }else if(tvValue.contains("x")){
                    val splitValue = tvValue.split("x")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvResult?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun onPercentage(view: View){
        var tvValue = tvResult?.text.toString()
        var one = tvValue
        var two = 100
        if(lastNumeric){
            val result = (one.toDouble() / two.toDouble()).toString()
            tvResult?.text = removeZeroAfterDot(result)
        }
    }

    // function to remove zero after dot
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        //check if the result has a value of zero after the decimal
        if (result.contains(".0"))
            //if it does remove the last two entry i.e the zero and the decimal
            value = result.substring(0, result.length -2)

        //then return value
        return value
    }

    //fun for onOperator added
    private fun onOperatorAdded(value: String) : Boolean {
        // return false if the value startsWith -
        return if (value.startsWith("-")){
            false
            // else true if it is contained in other parts
        }else{
            value.contains("÷")
                    ||value.contains("x")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }
}