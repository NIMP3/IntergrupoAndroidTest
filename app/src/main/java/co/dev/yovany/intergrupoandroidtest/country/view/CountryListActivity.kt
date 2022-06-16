package co.dev.yovany.intergrupoandroidtest.country.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import co.dev.yovany.intergrupoandroidtest.R
import co.dev.yovany.intergrupoandroidtest.common.MessageType
import co.dev.yovany.intergrupoandroidtest.country.CountryPresenter
import co.dev.yovany.intergrupoandroidtest.country.ICountryContract
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.databinding.ActivityCountryListBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class CountryListActivity : AppCompatActivity(), ICountryContract.ICountryListView {

    private lateinit var binding: ActivityCountryListBinding
    private lateinit var adapter: CountryRecyclerViewAdapter
    private lateinit var presenter: ICountryContract.ICountryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_InterGrupoAndroidTest)

        binding = ActivityCountryListBinding.inflate(layoutInflater)

        adapter = CountryRecyclerViewAdapter(R.layout.cardview_country, listener = { showCountryInMap(it)})
        binding.contentCountryList.rvSubsystemList.adapter = adapter

        presenter = CountryPresenter(this, applicationContext)
        presenter.getCountries()

        setContentView(binding.root)
    }

    override fun showCountries(countries: List<Country>) {
        adapter.countries = countries
        adapter.notifyDataSetChanged()
    }

    private fun showCountryInMap(country: Country) {
        val intent = Intent(this, CountryMapActivity::class.java)

        val bundle = Bundle()
        bundle.putString(CountryMapActivity.ARG_COUNTRY_NAME, country.name)
        intent.putExtras(bundle)

        startActivity(intent)
    }

    override fun showProgressbar() {
        binding.contentCountryList.apply {
            rvSubsystemList.visibility = View.INVISIBLE
            contentMessage.clMessage.visibility = View.VISIBLE

            showMessage(getString(R.string.message_status_load, "regiones..."), MessageType.LOAD)
        }
    }

    override fun hideProgressbar() {
        binding.contentCountryList.apply {
            rvSubsystemList.visibility = View.VISIBLE
            contentMessage.clMessage.visibility = View.GONE
        }
    }

    override fun showMessage(error: String?, messageType: MessageType) {
        binding.contentCountryList.contentMessage.apply {
            tvTitleError.text = messageType.data
            tvMessageError.text = error

            lavError.setAnimation(messageType.animation)
            lavError.animate()
            lavError.playAnimation()
        }
    }
}