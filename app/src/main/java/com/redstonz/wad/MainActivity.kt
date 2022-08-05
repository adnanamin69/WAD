package com.redstonz.wad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.redstonz.wad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ccp.registerCarrierNumberEditText(binding.number)
        binding.button.setOnClickListener {

            val number = binding.ccp.fullNumberWithPlus

            if(binding.ccp.isValidFullNumber)
            binding.webview.loadUrl("http://wa.me/$number")
else
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()

        }


    }
}