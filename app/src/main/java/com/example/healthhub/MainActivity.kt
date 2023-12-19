package com.example.healthhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.healthhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        binding.gotoDiabetesPrediction.setOnClickListener {
            startActivity(Intent(this@MainActivity, DiabetesPredictor::class.java))
        }
        binding.gotoHeartPrediction.setOnClickListener {
            startActivity(Intent(this@MainActivity, Heart::class.java))
        }
        binding.gotoStrokePrediction.setOnClickListener {
            startActivity(Intent(this@MainActivity, Stroke::class.java))
        }
        binding.gotoFitnessPrediction.setOnClickListener {
            startActivity(Intent(this@MainActivity, Fitness::class.java))
        }
    }
}