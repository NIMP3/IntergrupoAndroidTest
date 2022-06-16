package co.dev.yovany.intergrupoandroidtest.country.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country_name") val name: String,
    @SerializedName("country_short_name") val short_name: String,
    @SerializedName("country_phone_code") val phone_code: Int
)