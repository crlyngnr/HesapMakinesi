package com.example.hesapmakinesiodevi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.hesapmakinesiodevi.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumber = false
    var stateError = false
    var lastDot = false

    private lateinit var expression : Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun onAllClearClick(view: View) {
        binding.textViewIslem.text = ""
        binding.textViewSonuc.text = ""
        stateError = false
        lastDot = false
        lastNumber = false
        binding.textViewSonuc.visibility = View.GONE

    }
    fun onOperatorClick(view: View) {
        if (!stateError && lastNumber){
            binding.textViewIslem.append((view as Button).text)
            lastDot = false
            lastNumber = false
            onEqual()
        }
    }
    fun onDigitClick(view: View) {
        if (stateError){
            binding.textViewIslem.text = (view as Button).text
            stateError = false
        }else{
            binding.textViewIslem.append((view as Button).text)
        }
        lastNumber = true
        onEqual()
    }
    fun onBackClick(view: View) {
        binding.textViewIslem.text = binding.textViewIslem.text.toString().dropLast(1)
        try {
            val lastChar = binding.textViewIslem.text.toString().last()
            if (lastChar.isDigit()){
                onEqual()
            }
        }catch (e : Exception){
            binding.textViewSonuc.text= ""
            binding.textViewSonuc.visibility = View.GONE
            Log.e("Last Char Error!",e.toString())
        }
    }
    fun onClearClick(view: View) {
        binding.textViewIslem.text = ""
        lastNumber = false
    }
    fun onEqualClick(view: View) {
        onEqual()
        binding.textViewIslem.text = binding.textViewSonuc.text.toString().drop(1)
    }

    fun onEqual(){
        if (lastNumber && !stateError){
            val txt = binding.textViewIslem.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.textViewSonuc.visibility = View.VISIBLE
                binding.textViewSonuc.text = "=" + result.toString()
            }catch (ex : ArithmeticException){
                Log.e("Evaluate Error!",ex.toString())
                binding.textViewSonuc.text = "Error"
                stateError = true
                lastNumber = false
            }
        }
    }
}