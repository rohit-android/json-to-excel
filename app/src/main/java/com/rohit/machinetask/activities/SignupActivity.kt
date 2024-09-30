package com.rohit.machinetask.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.rohit.machinetask.R
import com.rohit.machinetask.database.User
import com.rohit.machinetask.viewmodels.UserViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val firstName = findViewById<EditText>(R.id.etFirstName)
        val lastName = findViewById<EditText>(R.id.etLastName)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val signUpBtn = findViewById<Button>(R.id.btnSignup)
        val signInBtn = findViewById<TextView>(R.id.btnSignIn)

        signUpBtn.setOnClickListener {
            val user = User(
                firstName = firstName.text.toString(),
                lastName = lastName.text.toString(),
                email = email.text.toString(),
                password = password.text.toString()
            )

            userViewModel.insertUser(user)
            Toast.makeText(this, "User Registered!", Toast.LENGTH_SHORT).show()
            finish()
        }

        signInBtn.setOnClickListener {
            finish()
        }
    }
}
