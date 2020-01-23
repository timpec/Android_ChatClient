package com.example.chatclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import java.net.Socket
import java.util.*
import java.io.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast


class ChatActivity : AppCompatActivity() {

    lateinit var s: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val listView = findViewById<ListView>(R.id.chatListView)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val textField = findViewById<EditText>(R.id.messageText)
        val users = ArrayList<UserData>()


        val myAdapter = MyCustomAdapter(this, users)

        listView.adapter = myAdapter


        Thread(Runnable {
            val message = textField.text.toString()
            s = Socket("192.168.8.103", 40001)


                Thread(Runnable {
                    var userNimi = intent.getStringExtra("userNameKey")

                    // val printer = PrintStream(s.getOutputStream(), true)
                    val printWriter = PrintWriter(BufferedWriter(OutputStreamWriter(s.getOutputStream())), true)



                    sendButton.setOnClickListener {
                        var teksti = textField.text.toString()
                        Log.d("server", teksti)
                        thread { printWriter.println(teksti) }
                        myAdapter.notifyDataSetChanged()
                        textField.text.clear()

                    }

                    Thread(Runnable {
                        val reader = Scanner(s.getInputStream())


                        printWriter.println(":user $userNimi")

                        while (true) {
                            val msg = reader.nextLine()
                            Log.d("server", msg)

                            runOnUiThread {

                                if (msg.contains(" - ")) {
                                    users.add(UserData(msg.substringBefore(" - "), msg.substringAfter(" - from")))
                                    myAdapter.notifyDataSetChanged()
                                } else if (msg == ":quit") {
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    users.add(UserData(msg, ""))
                                    myAdapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }).start()
                }).start()
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Thread(Runnable {
            val printWriter = PrintWriter(BufferedWriter(OutputStreamWriter(s.getOutputStream())), true)
            when (item.getItemId()) {
                R.id.users -> {
                    printWriter.println(":users")
                }
                R.id.history -> {
                    printWriter.println(":messages")
                }
                R.id.quit -> {
                    printWriter.println(":quit")

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    runOnUiThread {
                        Toast.makeText(this, "You have gone offline!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }).start()
        return true
    }

    override fun onDestroy() {

            thread {
                val printWriter = PrintWriter(BufferedWriter(OutputStreamWriter(s.getOutputStream())), true)
                printWriter.println(":quit")
                s.close()
                super.onDestroy()
            }

    }


}

