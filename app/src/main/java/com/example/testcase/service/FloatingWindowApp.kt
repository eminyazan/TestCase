package com.example.testcase.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.example.testcase.R
import com.example.testcase.WindowData
import com.example.testcase.databinding.FloatingLayoutBinding


class FloatingWindowApp : Service() {

    private lateinit var floatView: ViewGroup
    private lateinit var floatWindowLayoutParams: WindowManager.LayoutParams
    private lateinit var windowManager: WindowManager
    private var layoutType: Int? = null


    private lateinit var binding: FloatingLayoutBinding

    override fun onCreate() {
        super.onCreate()
        val metrics = applicationContext.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        floatView = inflater.inflate(R.layout.floating_layout, null) as ViewGroup

        binding = FloatingLayoutBinding.bind(floatView)



        binding.packageName = WindowData.floatPackageName

        println("Service package name ----> ${binding.packageName}")

        binding.btnWindowCloseWindow.setOnClickListener {
            windowManager.removeView(floatView)
            stopSelf()
        }


        layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_TOAST
        }
        floatWindowLayoutParams = WindowManager.LayoutParams(
            (width * 0.55f).toInt(),
            (height * 0.55f).toInt(),
            layoutType!!,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        floatWindowLayoutParams.gravity = Gravity.CENTER
        floatWindowLayoutParams.x = 0
        floatWindowLayoutParams.y = 0



        windowManager.addView(floatView, floatWindowLayoutParams)


    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
