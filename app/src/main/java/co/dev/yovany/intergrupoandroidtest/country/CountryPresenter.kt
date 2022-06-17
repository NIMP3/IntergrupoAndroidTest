package co.dev.yovany.intergrupoandroidtest.country

import android.content.Context
import android.util.Log
import co.dev.yovany.intergrupoandroidtest.common.IView
import co.dev.yovany.intergrupoandroidtest.common.ServerCallBack
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.country.model.UserToken

class CountryPresenter(view: IView, context: Context, private val interactor: CountryInteractor = CountryInteractor(context)) : ServerCallBack(view, context), ICountryContract.ICountryPresenter {
    override fun getCountries() {
        view.showProgressbar()
        interactor.getCountries(this)
    }

    override fun showCountries(countries: List<Country>) {
        view.hideProgressbar()
        (view as ICountryContract.ICountryListView).showCountries(countries)
    }

    override fun onCountriesFound(countries: List<Country>) {
        view.hideProgressbar()
        (view as ICountryContract.ICountryListView).showCountries(countries)
    }

    override fun onCountriesError() {
        interactor.getAccessToken(this)
    }

    override fun onUserTokenFound(userToken: UserToken) {
        interactor.processUserToken(userToken.auth_token)
        interactor.getCountries(this)
    }
}

interface ICountryContract {
    interface ICountryListView : IView {
        fun showCountries(countries: List<Country>)
    }

    interface ICountryPresenter {
        fun getCountries()
        fun showCountries(countries: List<Country>)

        fun onCountriesFound(countries: List<Country>)
        fun onCountriesError()
        fun onUserTokenFound(userToken: UserToken)
    }
}