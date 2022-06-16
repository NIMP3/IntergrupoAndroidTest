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
        if (NetworkUtility.wifiConnected(context)) repository.getCountries("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InVzZXJfZW1haWwiOiJ5b3ZhbnkuZGV2QGdtYWlsLmNvbSIsImFwaV90b2tlbiI6IkI2N3dhTC10cDk5bUpNcm1zV2RjOTdna3hnZzhPMmNnU21SRHhFTFkxQjZxUGRiWVlvY29MTGV6bDc5MjJ2cXdmdUkifSwiZXhwIjoxNjU1NDg2ODkyfQ.WsZmWxa3RxLyprdMYSclDAKfngAn37kVPtElyKFvWuE", serverCallBack)
        else serverCallBack.onNetworkError()
    }

    private fun processUserToken(token: String) {

    }
}