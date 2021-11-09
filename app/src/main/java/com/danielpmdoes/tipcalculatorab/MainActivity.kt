package com.danielpmdoes.tipcalculatorab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.danielpmdoes.tipcalculatorab.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
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
            binding.option20.id -> 0.20
            binding.option18.id -> 0.18
            binding.option15.id -> 0.15
            else -> 0.10
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