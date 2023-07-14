package com.momo.timeuptimetrackingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.momo.timeuptimetrackingapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {
            signInUser()
        }
    }

    private fun signInUser() {
        val email = binding.etSinInEmail.text.toString()
        val password = binding.etSinInPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userId = user.uid

                        // Save user information to the Firebase Realtime Database
                        val userRef = database.reference.child("users").child(userId)
                        userRef.child("email").setValue(email)

                        // Proceed to the DashboardActivity or any desired screen
                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}