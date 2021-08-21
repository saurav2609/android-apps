package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.login.R
import android.widget.Toast

class SignIn : AppCompatActivity() {
    var username: EditText? = null
    var password: EditText? = null
    var signInBtn: Button? = null
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        signInBtn = findViewById(R.id.signin)
        val bundle = intent.extras
        val uname = bundle!!.getString("username")
        val pwd = bundle.getString("password")
        signInBtn.setOnClickListener(View.OnClickListener {
            val user = username.getText().toString()
            val pass = password.getText().toString()
            if (user == uname && pass == pwd) {
                Toast.makeText(this@SignIn, "Success", Toast.LENGTH_SHORT).show()
            } else {
                count++
                if (count >= 3) {
                    signInBtn.setEnabled(false)
                } else {
                    Toast.makeText(this@SignIn, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        )
    }
}