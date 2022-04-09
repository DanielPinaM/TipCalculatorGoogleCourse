package com.danielpmdoes.tipcalculatorab.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.danielpmdoes.tipcalculatorab.R
import com.danielpmdoes.tipcalculatorab.data.Constants
import com.danielpmdoes.tipcalculatorab.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initUI()
    }
    
    private fun initUI() {
        binding.calculateButton.setOnClickListener { 
            calculateTip() 
        }
        
        binding.clearButton.setOnClickListener {
            showTipResult(getString(R.string.tip_amount_tbd))
            binding.costOfService.setText("")
            binding.tipOptions.clearCheck()
        }
    }

    private fun calculateTip(){
        val costOfService = binding.costOfService.text.toString().toDoubleOrNull()

        if (costOfService == null){
            Toast.makeText(this, getString(R.string.please_cost_first), Toast.LENGTH_SHORT).show()
            showTipResult(getString(R.string.tip_amount_tbd))
            return
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            binding.option20.id -> Constants.OPTION_20
            binding.option18.id -> Constants.OPTION_18
            binding.option15.id -> Constants.OPTION_15
            else -> Constants.OPTION_10
        }

        var tip = tipPercentage * costOfService

        if (binding.roundUpSw.isChecked)
            tip = ceil(tip)

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        showTipResult(formattedTip)
    }

    private fun showTipResult(string: String) {
        binding.tipResult.text = getString(R.string.tip_amount, string)
    }
}