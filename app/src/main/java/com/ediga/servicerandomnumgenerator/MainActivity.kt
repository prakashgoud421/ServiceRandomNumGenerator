package com.ediga.servicerandomnumgenerator

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var myService: Randomservice? = null
    var isBound = false
    val randomNumberTV = findViewById(R.id.randomNumber) as TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, Randomservice.MyService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        if(isBound) setRandomNuberToView()
    }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as Randomservice.MyService
            myService = binder.getService()
            isBound = true
        }

    }

    fun setRandomNuberToView() {
        val randomNumber = myService?.getRandomNumber()
        randomNumberTV.text = randomNumber.toString()
    }
}