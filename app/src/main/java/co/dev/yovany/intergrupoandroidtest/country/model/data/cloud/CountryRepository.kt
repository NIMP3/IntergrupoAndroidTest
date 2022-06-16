package co.dev.yovany.intergrupoandroidtest.country.model.data.cloud

import co.dev.yovany.intergrupoandroidtest.common.EndPoints
import co.dev.yovany.intergrupoandroidtest.common.IService
import co.dev.yovany.intergrupoandroidtest.common.ServerCallBack
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.country.model.UserToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryRepository {

    private var service: IService = Retrofit.Builder()
        .baseUrl(EndPoints.DOMAIN)
        .build()
        .create(IService::class.java)

    fun getAccessToken(api_token: String, user_email: String, serverCallBack: ServerCallBack) {
        val call = service.getAccessToken(api_token, user_email)
        call.enqueue(object : Callback<UserToken> {
            override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {

            }

            override fun onFailure(call: Call<UserToken>, t: Throwable) {
                serverCallBack.onServerError("XB3412")
            }

        })
    }

    fun getCountries(token: String, serverCallBack: ServerCallBack) {
        val call = service.getCountries(token)
        call.enqueue(object : Callback<List<Country>>{
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {

            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                serverCallBack.onServerError("XB3412")
            }

        })
    }
}