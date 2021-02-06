package com.example.demoevaluationnandita.appdata

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.StrictMode
import androidx.multidex.MultiDex
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.demoevaluationnandita.appdata.utils.AppLogger


class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        AppLogger.init()
        AndroidNetworking.initialize(getApplicationContext())

        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        instance = this

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
    }

    companion object Singleton {

        val TAG: String = AppController::class.java.simpleName

        @get:Synchronized
        lateinit var instance: AppController
            private set

        val appContext: Context?
            get() = instance.applicationContext
    }

}
