package co.dev.yovany.intergrupoandroidtest.country

import android.content.Context
import android.util.Log
import co.dev.yovany.intergrupoandroidtest.BuildConfig
import co.dev.yovany.intergrupoandroidtest.common.NetworkUtility
import co.dev.yovany.intergrupoandroidtest.common.SecurityUtility
import co.dev.yovany.intergrupoandroidtest.common.ServerCallBack
import co.dev.yovany.intergrupoandroidtest.country.model.data.CountryRepository

class CountryInteractor(
    val context: Context,
    private val repository: CountryRepository = CountryRepository(),
    private val securityUtility: SecurityUtility = SecurityUtility(context)
) {
    fun getAccessToken(serverCallBack: ServerCallBack) {
        if (NetworkUtility.wifiConnected(context)) repository.getAccessToken(BuildConfig.API_TOKEN, BuildConfig.EMAIL, serverCallBack)
        else serverCallBack.onNetworkError()
    }

    fun getCountries(serverCallBack: ServerCallBack) {
        if (NetworkUtility.wifiConnected(context)) {
            securityUtility.getUserToken()?.let { token ->
                repository.getCountries(token, serverCallBack)
            } ?: kotlin.run {
                getAccessToken(serverCallBack)
            }
        }
        else serverCallBack.onNetworkError()
    }

    fun processUserToken(token: String) { securityUtility.encryptUserToken(token) }
}