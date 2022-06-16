package co.dev.yovany.intergrupoandroidtest.country.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.dev.yovany.intergrupoandroidtest.R
import co.dev.yovany.intergrupoandroidtest.common.MessageType
import co.dev.yovany.intergrupoandroidtest.country.CountryPresenter
import co.dev.yovany.intergrupoandroidtest.country.ICountryContract
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.databinding.ActivityCountryListBinding

class CountryListActivity : AppCompatActivity(), ICountryContract.ICountryListView {

    private lateinit var binding: ActivityCountryListBinding
    private lateinit var adapter: CountryRecyclerViewAdapter
    private lateinit var presenter: ICountryContract.ICountryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryListBinding.inflate(layoutInflater)

        adapter = CountryRecyclerViewAdapter(R.layout.cardview_country)
        binding.contentCountryList.rvSubsystemList.adapter = adapter

        presenter = CountryPresenter(this, applicationContext)

        setContentView(binding.root)
    }

    override fun showCountries(countries: List<Country>) {
        adapter.countries = countries
        adapter.notifyDataSetChanged()
    }

    override fun showProgressbar() {
        binding.contentCountryList.apply {
            rvSubsystemList.visibility = View.INVISIBLE
            contentMessage.clMessage.visibility = View.VISIBLE
        }
    }

    override fun hideProgressbar() {
        binding.contentCountryList.apply {
            rvSubsystemList.visibility = View.VISIBLE
            contentMessage.clMessage.visibility = View.GONE
        }
    }

    override fun showMessage(error: String?, messageType: MessageType) {

    }
}