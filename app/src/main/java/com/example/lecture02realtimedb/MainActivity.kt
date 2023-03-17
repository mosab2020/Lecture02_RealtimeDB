package com.example.lecture02realtimedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lecture02realtimedb.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var countId: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference()

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val id = binding.etId.text.toString()
            val age = binding.etAge.text.toString()

            val person = hashMapOf(
                "name" to name,
                "id" to id,
                "age" to age
            )

            myRef.child("person").child("$countId").setValue(person)
            countId++
            Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()

            binding.etId.setText("")
            binding.etName.setText("")
            binding.etAge.setText("")

        }

        binding.btnGetData.setOnClickListener {
            // Read from the database
            myRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.getValue()
                    binding.tvData.text = value.toString()
                    Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
}