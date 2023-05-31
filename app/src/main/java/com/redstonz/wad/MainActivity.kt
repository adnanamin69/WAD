package com.redstonz.wad

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redstonz.wad.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ccp.registerCarrierNumberEditText(binding.number)
        binding.whatsapp.setOnClickListener {

            val number = binding.ccp.fullNumberWithPlus

            if (binding.ccp.isValidFullNumber)
                openWhatsApp(number)
            else
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()

        }


    }


    fun openWhatsApp(number: String) {
        try {
            val url = "whatsapp://send?phone=$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}