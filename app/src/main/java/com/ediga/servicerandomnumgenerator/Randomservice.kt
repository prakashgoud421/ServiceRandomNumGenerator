package com.ediga.servicerandomnumgenerator

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import kotlin.random.Random
import kotlin.random.nextInt

class Randomservice : Service() {
    private var myBinder = MyService()

    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class MyService : Binder(){
        fun getService() : Randomservice {
            return this@Randomservice
        }
    }

    fun getRandomNumber(): Int {
       return Random.nextInt(0..120)
    }
}