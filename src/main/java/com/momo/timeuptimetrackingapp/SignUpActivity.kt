package com.momo.timeuptimetrackingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.momo.timeuptimetrackingapp.databinding.ActivitySignUpBinding
import android.content.Intent

class SignUpActivity : AppCompatActivity() {
    private var binding: ActivitySignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvLoginPage?.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        binding?.btnSignUp?.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        }
    }

