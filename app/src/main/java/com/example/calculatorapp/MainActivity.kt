package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError=false
    var lastOut=false

    private lateinit var expression:Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTextView.text=binding.resultTextview.text.toString().drop(1)

    }



    fun onAllclearClick(view: View) {

        binding.dataTextView.text=""
        binding.resultTextview.text=""
        stateError=false
        lastOut=false
        lastNumeric=false
        binding.resultTextview.visibility=View.GONE

    }




    fun onOperatorClick(view: View) {

        if (!stateError && lastNumeric){

            binding.dataTextView.append((view as Button).text)
            lastOut=false
            lastNumeric=false
            onEqual()
        }
    }




    fun onClearClick(view: View) {

        binding.dataTextView.text=""
        lastNumeric=false

    }





    fun onBackClick(view: View) {

        binding.dataTextView.text=binding.dataTextView.text.toString().dropLast(1)

        try {
            val lastChar=binding.dataTextView.text.toString().last()

            if (lastChar.isDigit()){
                onEqual()
            }
        }catch (e : Exception){
            binding.resultTextview.text=""
            binding.resultTextview.visibility=View.GONE
            Log.e("Last Char Error",e.toString())
        }

    }






    fun onDigitClick(view: View) {

        if (stateError){

            binding.dataTextView.text=(view as Button).text
            stateError=false
        }

        else{
            binding.dataTextView.append((view as Button).text)
        }

        lastNumeric=true
        onEqual()

    }

    fun onEqual(){
        if(lastNumeric && !stateError){
            val txt=binding.dataTextView.text.toString()

            expression=ExpressionBuilder(txt).build()

            try {
                val result=expression.evaluate()
                binding.resultTextview.visibility   = View.VISIBLE
                binding.resultTextview.text="=" + result.toString()
            }catch (ex:java.lang.ArithmeticException){
                Log.e("evaluate error",ex.toString())
                binding.resultTextview.text="Error"
                stateError=true
                lastNumeric=false
            }

        }
    }

}