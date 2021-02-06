package com.example.demoevaluationnandita.appdata.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import com.example.demoevaluationnandita.appdata.AppController
import com.kaopiz.kprogresshud.KProgressHUD


object Utils {

    fun showLoader(context: Context): KProgressHUD {
        val hud = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        hud.show()
        return hud
    }

    fun dismissLoader(hud: KProgressHUD) {
        hud.dismiss()
    }

    fun isInternetON(): Boolean {
        val connectivityManager =
                AppController.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun showAlertDialog(context: Context, message: String): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
        val dialog = builder.create()
        dialog.show()
        return dialog
    }

}