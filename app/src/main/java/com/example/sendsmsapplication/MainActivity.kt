package com.example.sendsmsapplication

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.*

class MainActivity : AppCompatActivity() {
    lateinit var message: EditText
    lateinit var phone:  EditText
    lateinit var send: Button

    var userMessage: String = ""
    var userPhone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.editTextMessage)
        phone = findViewById(R.id.editTextPhone)
        send = findViewById(R.id.buttonSend)

        send.setOnClickListener {
            userMessage = message.text.toString()
            userPhone = phone.text.toString()

            sendSMS(userMessage, userPhone)
        }
    }

    private fun sendSMS(userMsg: String, userPhone: String) {
        if (checkSelfPermission(
                this,
                android.Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 1)
        }else {
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                smsManager = this.getSystemService(SmsManager::class.java)
            }else {
                smsManager = SmsManager.getDefault()
            }

            smsManager.sendTextMessage(userPhone, null, userMsg, null, null)

            Toast.makeText(applicationContext, "Message send", Toast
                .LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                smsManager = this.getSystemService(SmsManager::class.java)
            }else {
                smsManager = SmsManager.getDefault()
            }

            smsManager.sendTextMessage(userPhone, null, userMessage, null, null)

            Toast.makeText(applicationContext, "Message send", Toast
                .LENGTH_LONG).show()
        }
    }
}