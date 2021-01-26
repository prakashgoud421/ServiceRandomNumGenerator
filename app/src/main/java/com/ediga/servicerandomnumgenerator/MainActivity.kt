package com.ediga.servicerandomnumgenerator

import android.content.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var isBound = false
    var randomNumberTV: TextView? = null
    var randomNumberSend: Messenger? = null
    var randomNumberReceive: Messenger? = null
    var intentService : Intent? = Intent()
    var messageSendRandomNumber : Message? = null
    val MSG_SAY_HELLO : Int =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randomNumberTV = findViewById(R.id.randomNumber) as TextView
        Log.e(TAG, "onCreate: ")
        intentService?.setComponent(ComponentName("com.ediga.serviceappboundservice","com.ediga.serviceappboundservice.BoundRandomService"))

    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: "+ (intentService?.component ?: componentName))
        if(!isBound) {
            bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            randomNumberReceive = null
            randomNumberSend = null
            isBound = false
            Log.e(TAG, "onServiceDisconnected: ")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            Log.e(TAG, "onServiceConnected: ")
            randomNumberSend = Messenger(service)
            randomNumberReceive = Messenger(ReceiveRandomNumber())
            messageSendRandomNumber = Message.obtain(null, MSG_SAY_HELLO)
            Log.e(TAG, "messageSendRandomNumber: "+messageSendRandomNumber)
            randomNumberReceive!!.send(messageSendRandomNumber)

        }
    }

    fun setRandomNuberToView(randomNumber: Int) {

        Log.e(TAG, "setRandomNuberToView: randomNumber--> " + randomNumber)

        randomNumberTV?.setText(randomNumber.toString())
        Toast.makeText(this@MainActivity, "Random Number is Set", Toast.LENGTH_LONG)
    }

    inner class ReceiveRandomNumber : Handler() {
        override fun handleMessage(msg: Message) {

            var MSG_SAY_HELLO = 1
            when (msg.what) {
                MSG_SAY_HELLO -> {
                    val randomNumber = msg.arg1
                    setRandomNuberToView(randomNumber)
                }
                else -> super.handleMessage(msg)
            }
        }
    }
}
