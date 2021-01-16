package com.ediga.servicerandomnumgenerator

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var myService: Randomservice? = null
    var isBound = false
    var randomNumberTV : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       randomNumberTV = findViewById(R.id.randomNumber) as TextView

        // Bind to LocalService
        Intent(this, Randomservice::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }
     var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            Log.e(TAG, "onServiceDisconnected: ")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as Randomservice.MyService
            myService = binder.getService()
            isBound = true
            Log.e(TAG, "onServiceConnected: myService--> "+myService)
            Toast.makeText(this@MainActivity,"Service Bounded",Toast.LENGTH_LONG)

            if(isBound) setRandomNuberToView()
        }

    }

    fun setRandomNuberToView() {
        val randomNumber = myService?.getRandomNumber()
        Log.e(TAG, "setRandomNuberToView: randomNumber--> "+randomNumber)

        randomNumberTV?.setText(randomNumber.toString())
        Toast.makeText(this@MainActivity,"Random Number is Set",Toast.LENGTH_LONG)
    }
}