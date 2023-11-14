package com.example.app_hugo_desafio08

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build

class MainActivity : AppCompatActivity() {

    private val CHANEL_ID = "MyChannel"
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        createNot()

        button.setOnClickListener{
            showNot()
        }
    }

    private fun showNot(){
        var intent = Intent(this, MainActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        var notification = NotificationCompat.Builder(this, CHANEL_ID)
            .setSmallIcon(com.google.android.material.R.drawable.mtrl_checkbox_button_icon_checked_unchecked)
            .setContentTitle("AVISO! Olha ai bobão")
            .setContentText("Essa é uma notificação para te lembrar de fazer o desafio!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            button.setText("Deu Ruim")
            return
        }
        notificationManager.notify(1,notification)
    }


    private fun createNot (){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channel = NotificationChannel(
                CHANEL_ID,
                "MyChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para Descrições"
                enableLights(true)
                lightColor = Color.MAGENTA
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}