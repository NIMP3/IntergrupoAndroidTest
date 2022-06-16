package co.dev.yovany.intergrupoandroidtest.country

import android.content.Context
import co.dev.yovany.intergrupoandroidtest.common.IView
import co.dev.yovany.intergrupoandroidtest.common.ServerCallBack
import co.dev.yovany.intergrupoandroidtest.country.model.Country

class CountryPresenter(view: IView, context: Context, private val interactor: CountryInteractor = CountryInteractor(context)) : ServerCallBack(view, context), ICountryContract.ICountryPresenter {
    override fun getCountries() {
        view.showProgressbar()
        interactor.getCountries(this)
    }

    override fun showCountries(countries: List<Country>) {
        view.hideProgressbar()
        (view as ICountryContract.ICountryListView).showCountries(countries)
    }
}

interface ICountryContract {
    interface ICountryListView : IView {
        fun showCountries(countries: List<Country>)
    }

    interface ICountryPresenter {
        fun getCountries()
        fun showCountries(countries: List<Country>)
    }
}