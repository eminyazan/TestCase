package com.example.testcase

import com.example.testcase.service.FloatingWindowApp
import android.app.AlertDialog
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.testcase.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var tvPackageName: TextView
    private lateinit var btnPermission: Button
    private lateinit var btnBackground: Button
    private lateinit var dialog: AlertDialog

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        tvPackageName = findViewById(R.id.tvPackageName)
//        btnPermission = findViewById(R.id.btnPermission)
//        btnBackground = findViewById(R.id.btnRunBakcground)
//
//        tvPackageName.text = packageName
//
//
//        btnPermission.setOnClickListener {
//            requestPermission()
//        }
//
//        btnBackground.setOnClickListener {
//            WindowData.floatPackageName = packageName
//            println("Window data ----------> ${WindowData.floatPackageName}")
//            runService(appGetPackageName())
//        }


    }

//    private fun appGetPackageName(): String {
//        var mString = ""
//        val usm = getSystemService(android.content.Context.USAGE_STATS_SERVICE) as UsageStatsManager
//        val time = System.currentTimeMillis()
//        val appList = usm.queryUsageStats(
//            UsageStatsManager.INTERVAL_DAILY,
//            time - 1000 * 1000,
//            time
//        )
//
//        if (appList != null && appList.size > 0) {
//            val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
//            for (usageStats in appList) {
//                mySortedMap[usageStats.lastTimeUsed] = usageStats
//            }
//            if (!mySortedMap.isEmpty()) {
//                mString = mySortedMap[mySortedMap.lastKey()]!!.packageName
//                println("Son App ---> $mString")
//            }
//        }
//        return mString
//    }
//
//    private fun runService(packageName:String) {
//        //kill service if its running
//        this@MainActivity.stopService(Intent(applicationContext, FloatingWindowApp::class.java))
//        this@MainActivity.finish()
//        // update our object data
//        WindowData.floatPackageName = packageName
//        //start service
//        this@MainActivity.startService(Intent(applicationContext, FloatingWindowApp::class.java))
//        this@MainActivity.finish()
//    }


    //    private fun checkPermission(): Boolean {
//        // we have permission after M otherwise it is false automatically
//        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            Settings.ACTION
//        } else {
//            return false
//        }
//    }
//    private fun requestPermission() {
//        // show dialog for permission
//        val builder = AlertDialog.Builder(this)
//        builder.setCancelable(true)
//        builder.setTitle("izin verrrr")
//        builder.setMessage("izin ver")
//        builder.setPositiveButton("hadi") { _, _ ->
//
//            // open settings with package name
//            val intent = Intent(
//                Settings.ACTION_USAGE_ACCESS_SETTINGS,
//                Uri.parse("package:${packageName}")
//            )
//            startActivityForResult(intent, RESULT_OK)
//        }
//        dialog = builder.create()
//        dialog.show()
//
//    }

//    fun getTopAppName(context: Context): String? {
//        val mActivityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
//        var strName: String? = ""
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                strName = getLollipopFGAppPackageName(context)
//            } else {
//                strName = mActivityManager.getRunningTasks(1)[0].topActivity!!.className
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return strName
//    }
}