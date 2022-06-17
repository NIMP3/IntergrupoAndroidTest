package co.dev.yovany.intergrupoandroidtest.country.model.data

import android.util.Log
import co.dev.yovany.intergrupoandroidtest.common.EndPoints
import co.dev.yovany.intergrupoandroidtest.common.IService
import co.dev.yovany.intergrupoandroidtest.common.ServerCallBack
import co.dev.yovany.intergrupoandroidtest.country.ICountryContract
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.country.model.ServerError
import co.dev.yovany.intergrupoandroidtest.country.model.UserToken
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class CountryRepository {

    fun getAccessToken(api_token: String, user_email: String, serverCallBack: ServerCallBack) {
        serverCallBack as ICountryContract.ICountryPresenter

        val call = createService(true).getAccessToken(api_token, user_email)
        call.enqueue(object : Callback<UserToken> {
            override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        serverCallBack.onUserTokenFound(it)
                    } ?: kotlin.run {serverCallBack.onSystemError("X3D452")}
                }
                else serverCallBack.onServerError("X234DE")
            }

            override fun onFailure(call: Call<UserToken>, t: Throwable) {
                serverCallBack.onServerError("XB3412")
            }

        })
    }

    fun getCountries(token: String, serverCallBack: ServerCallBack) {
        serverCallBack as ICountryContract.ICountryPresenter

        val call = createService(false).getCountries("Bearer $token")
        call.enqueue(object : Callback<List<Country>>{
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        serverCallBack.onCountriesFound(it)
                    } ?: kotlin.run { serverCallBack.onSystemError("X3D452") }
                }
                else processServerError(response.errorBody()?.string(), serverCallBack)
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                serverCallBack.onServerError("XB3412")
            }

        })
    }

    private fun processServerError(data: String?, serverCallBack: ServerCallBack) {
        serverCallBack as ICountryContract.ICountryPresenter

        data?.let {
            try {
                val serverError : ServerError = Gson().fromJson(data, ServerError::class.java)
                if (serverError.error.name == "TokenExpiredError") serverCallBack.onCountriesError()
                else serverCallBack.onServerError("X234DE")
            } catch (e: JsonSyntaxException) { serverCallBack.onServerError("X234DE") }
        } ?: kotlin.run { serverCallBack.onServerError("X234DE")}
    }

    private fun createService(hasLimit: Boolean): IService {
        val dispatcher = Dispatcher(Executors.newFixedThreadPool(20))
        dispatcher.maxRequests = if (hasLimit) 2 else 20
        dispatcher.maxRequestsPerHost = 1

        val okHttpClient = OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .connectionPool(ConnectionPool(100, 30, TimeUnit.SECONDS))
            .build()

        return Retrofit.Builder()
            .baseUrl(EndPoints.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(IService::class.java)
    }
}