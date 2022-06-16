package co.dev.yovany.intergrupoandroidtest.country

import android.content.Context
import co.dev.yovany.intergrupoandroidtest.BuildConfig
import co.dev.yovany.intergrupoandroidtest.common.IService
import co.dev.yovany.intergrupoandroidtest.common.NetworkUtility
import co.dev.yovany.intergrupoandroidtest.common.ServerCallBack
import co.dev.yovany.intergrupoandroidtest.country.model.data.cloud.CountryRepository

class CountryInteractor(
    val context: Context,
    val repository: CountryRepository = CountryRepository()
) {
    fun getAccessToken(serverCallBack: ServerCallBack) {
        if (NetworkUtility.wifiConnected(context)) repository.getAccessToken(BuildConfig.API_TOKEN, BuildConfig.EMAIL, serverCallBack)
        else serverCallBack.onNetworkError()
    }

    fun getCountries(serverCallBack: ServerCallBack) {
        if (NetworkUtility.wifiConnected(context)) repository.getCountries("", serverCallBack)
        else serverCallBack.onNetworkError()
    }

    private fun processUserToken(token: String) {

    }
}