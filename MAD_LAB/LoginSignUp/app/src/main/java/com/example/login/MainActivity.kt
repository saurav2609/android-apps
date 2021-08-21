package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.login.R
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.login.SignIn
import android.widget.Toast
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    var username: EditText? = null
    var password: EditText? = null
    var signUpBtn: Button? = null
    var regularExpr = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!])[A-Za-z\\d@$!]{8,}$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        signUpBtn = findViewById(R.id.signup)
        signUpBtn.setOnClickListener(View.OnClickListener {
            val uname = username.getText().toString()
            val pwd = password.getText().toString()
            if (validatePassword(pwd)) {
                val bundle = Bundle()
                bundle.putString("username", uname)
                bundle.putString("password", pwd)
                val intent = Intent(this@MainActivity, SignIn::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Invaild Password", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun validatePassword(pwd: String?): Boolean {
        val pattern = Pattern.compile(regularExpr)
        val matcher = pattern.matcher(pwd)
        return matcher.matches()
    }
}