package com.example.chatclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logInButton = findViewById<Button>(R.id.buttonLogin)
        val editText = findViewById<EditText>(R.id.usernameText)

        logInButton.setOnClickListener{
            if(editText.text.length == 0){
                val warning = "You need to give your username!"
                Toast.makeText(this, warning, Toast.LENGTH_SHORT).show()

            }else{

                val userIntent = Intent(this, ChatActivity::class.java).apply {
                    val userNimi = editText.text.toString()
                    putExtra("userNameKey", userNimi)
                }
                    startActivity(userIntent)
            }
        }
    }
}
