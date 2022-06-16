package co.dev.yovany.intergrupoandroidtest.country.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import co.dev.yovany.intergrupoandroidtest.R
import co.dev.yovany.intergrupoandroidtest.common.MessageType
import co.dev.yovany.intergrupoandroidtest.country.CountryPresenter
import co.dev.yovany.intergrupoandroidtest.country.ICountryContract
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.databinding.ActivityCountryListBinding

class CountryListActivity : AppCompatActivity(), ICountryContract.ICountryListView {

    private var countries = emptyList<Country>()
    private lateinit var binding: ActivityCountryListBinding
    private lateinit var adapter: CountryRecyclerViewAdapter
    private lateinit var presenter: ICountryContract.ICountryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_InterGrupoAndroidTest)
        binding = ActivityCountryListBinding.inflate(layoutInflater)

        this.setSupportActionBar(binding.actionBar.toolbar)
        adapter = CountryRecyclerViewAdapter(R.layout.cardview_country, listener = { showCountryInMap(it)})
        binding.contentCountryList.rvSubsystemList.adapter = adapter

        presenter = CountryPresenter(this, applicationContext)
        presenter.getCountries()

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchView: SearchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                showProgressbar()
                newText?.let { searchCountryByName(it) }
                return false
            }

        })

        return true
    }

    override fun showCountries(countries: List<Country>) {
        this.countries = countries
        adapter.loadCountries(countries)
    }

    private fun searchCountryByName(name: String) {
        val filteredList = mutableListOf<Country>()
        countries.forEach { country ->
            if (country.name.lowercase().contains(name.lowercase())) filteredList.add(country)
        }

        if (filteredList.isEmpty()) showMessage("${getString(R.string.message_no_data)} datos.", MessageType.INFO)
        else {
            hideProgressbar()
            adapter.loadCountries(filteredList)
        }
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