package co.dev.yovany.intergrupoandroidtest.common

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtility {
    fun wifiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return run {
            val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            wifi != null && wifi.isConnected || mobile != null && mobile.isConnected
        }
    }
}