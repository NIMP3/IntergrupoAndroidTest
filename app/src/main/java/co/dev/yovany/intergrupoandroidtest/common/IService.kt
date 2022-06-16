package co.dev.yovany.intergrupoandroidtest.common

import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.country.model.UserToken
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface IService {
    @GET(EndPoints.GET_ACCESS_TOKEN)
    fun getAccessToken(
        @Header("api-token") api_token: String,
        @Header("user-email") email: String) : Call<UserToken>

    @GET(EndPoints.GET_COUNTRIES)
    fun getCountries(@Header("Authorization") token: String) : Call<List<Country>>
}