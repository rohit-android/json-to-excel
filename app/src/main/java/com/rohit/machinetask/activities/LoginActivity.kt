package com.rohit.machinetask.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rohit.machinetask.R
import com.rohit.machinetask.viewmodels.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val btnSignUp = findViewById<TextView>(R.id.btnSignUp)

        loginBtn.setOnClickListener {
            val emailInput = email.text.toString()
            val passwordInput = password.text.toString()

            userViewModel.login(emailInput, passwordInput).observe(this) { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
