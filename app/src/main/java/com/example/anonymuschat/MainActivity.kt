package com.example.anonymuschat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anonymuschat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth =  Firebase.auth

        binding.buttonSignOut.setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}