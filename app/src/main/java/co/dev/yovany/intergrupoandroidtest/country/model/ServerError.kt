package co.dev.yovany.intergrupoandroidtest.country.model

data class ServerError(val error: DataError)
data class DataError(val name: String, val message: String)
