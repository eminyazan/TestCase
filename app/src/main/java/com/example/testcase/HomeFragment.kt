package com.example.testcase

import android.app.ActivityManager
import android.app.AlertDialog
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testcase.databinding.FragmentHomeBinding
import com.example.testcase.service.FloatingWindowApp
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    private lateinit var dialog: AlertDialog


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun observeEvents() {

        binding.tvAppName.text = activity?.packageName

        // kill service
        if (isServiceRunning()) {
            activity?.stopService(Intent(requireContext(), FloatingWindowApp::class.java))
        }


        if (checkOverlayPermission()) {
            //hide permission button we have permission
            hideOverlayPermissionButton()

            if (checkUsageStatsPermission()) {
                hideOverlayPermissionButton()
                hideTaskPermissionButton()

                showRunServiceButton()
            }
            else {
                hideRunServiceButton()
                hideOverlayPermissionButton()

                showTaskPermissionButton()
            }
        } else {
            //Show button we do not have permission
            showOverlayPermissionButton()

            hideRunServiceButton()
            hideTaskPermissionButton()
        }


    }

    private fun showTaskPermissionButton() {
        binding.btnUsagePermission.isVisible = true
        binding.btnUsagePermission.setOnClickListener {
            requestUsagePermission()
        }
    }
    private fun hideTaskPermissionButton() {
        binding.btnUsagePermission.isVisible = false
    }

    // ------------------------------------- W I D G E T S --------------------------------------//


    private fun hideOverlayPermissionButton() {
        binding.btnOverlayPermissionButton.isVisible = false
    }

    private fun showOverlayPermissionButton() {
        binding.btnOverlayPermissionButton.isVisible = true
        binding.btnOverlayPermissionButton.setOnClickListener {
            requestOverlayPermission()
        }
    }

    private fun hideRunServiceButton() {
        binding.btnRunService.isVisible = false
    }

    private fun showRunServiceButton() {
        binding.btnRunService.isVisible = true
        binding.btnRunService.setOnClickListener {
            val appName = appGetPackageName()
            println("App Name ------> $appName")
            showInWindow(appName)
        }
    }


    // ------------------------------------- W I D G E T S --------------------------------------//

    // ------------------------------------- P E R M I S S I O N ------------------------------------//

    private fun isServiceRunning(): Boolean {
        // is floating window is working
        activity?.let {
            val manager = it.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (FloatingWindowApp::class.java.name == service.service.packageName) {
                    return true
                }
            }
        }

        return false
    }

    private fun requestOverlayPermission() {
        val context = requireContext()
        // show dialog for permission
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle("Overlay izni gerekli")
        builder.setMessage("Uygulamaya izin vermelisiniz")
        builder.setPositiveButton("Tamam") { _, _ ->

            // open settings with package name
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context.packageName}")
            )
            startActivityForResult(intent, AppCompatActivity.RESULT_OK)
        }
        dialog = builder.create()
        dialog.show()

    }

    private fun checkOverlayPermission(): Boolean {
        // we have permission after M otherwise it is false automatically
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(requireContext())
        } else {
            return false
        }
    }


    private fun requestUsagePermission() {
        val context = requireContext()
        // show dialog for permission
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle("Usage izni gerekli")
        builder.setMessage("Uygulamaya izin vermelisiniz")
        builder.setPositiveButton("Tamam") { _, _ ->

            // open settings with package name
            val intent = Intent(
                Settings.ACTION_USAGE_ACCESS_SETTINGS,
                Uri.parse("package:${context.packageName}")
            )
            startActivityForResult(intent, AppCompatActivity.RESULT_OK)
        }
        dialog = builder.create()
        dialog.show()

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkUsageStatsPermission(): Boolean {
        var res = false
        activity?.let {
            val appOpsManager = it.getSystemService(AppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
            // `AppOpsManager.checkOpNoThrow` is deprecated from Android Q
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOpsManager.unsafeCheckOpNoThrow(
                    "android:get_usage_stats",
                    android.os.Process.myUid(), it.opPackageName
                )
            } else {
                appOpsManager.checkOpNoThrow(
                    "android:get_usage_stats",
                    android.os.Process.myUid(), it.opPackageName
                )
            }
            res = mode == AppOpsManager.MODE_ALLOWED
            println("Usage izni ----> $res")
        }
        return res

    }

    private fun showInWindow(packageName: String) {
        //kill service if its running
        activity?.stopService(Intent(requireContext(), FloatingWindowApp::class.java))
        activity?.finish()
        // update our object data
        WindowData.floatPackageName = packageName
        //start service
        activity?.startService(Intent(context, FloatingWindowApp::class.java))
        activity?.finish()

    }

    private fun appGetPackageName(): String {
        var mString = ""
        activity?.let {
            val usm = it.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val time = System.currentTimeMillis()
            val appList = usm.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                time - 1000 * 1000,
                time
            )

            if (appList != null && appList.size > 0) {
                val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
                for (usageStats in appList) {
                    mySortedMap[usageStats.lastTimeUsed] = usageStats
                }
                if (!mySortedMap.isEmpty()) {
                    mString = mySortedMap[mySortedMap.lastKey()]!!.packageName
                    println("Son App ---> $mString")
                }
            }
        }


        return mString
    }

    // ------------------------------------- P E R M I S S I O N--------------------------------------//


}