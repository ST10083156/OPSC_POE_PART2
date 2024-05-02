package com.example.opsc_poe_part2_timewise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.opsc_poe_part2_timewise.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var userID : String

    //login activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        //on button click to redirect to registration page
        binding.regPageBtn.setOnClickListener{var intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
            finish()}
        binding.loginBtn.setOnClickListener{login()}


    }

    //login function with firebase authentication
    private fun login(){
        val email = binding.emailET.text.toString()
        val password = binding.passwordET.text.toString()

        //attempts to sign user in with email and password
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful)
            {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, CategoriesPage::class.java)
                startActivity(intent)
                finish()
                }
            else
            {
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
            }
            }

        }
    }
