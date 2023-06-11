package com.example.spanish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spanish.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun changeTextViewLogoTheme(s: String?) {
        binding.textLogo.text = s
    }

    fun changeTextViewLogoMode(s: String?) {
        if (s != null)
            binding.textLogo.append(" : $s")
    }
}