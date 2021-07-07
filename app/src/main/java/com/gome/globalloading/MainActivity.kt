package com.gome.globalloading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gome.globalloading.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.button.setOnClickListener {
            startActivity(Intent(this, LoadingSuccessActivity::class.java))
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, LoadingFailActivity::class.java))
        }

        binding.button3.setOnClickListener {

        }
    }
}