package co.dev.yovany.intergrupoandroidtest.country.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.databinding.CardviewCountryBinding

class CountryRecyclerViewAdapter(private val resource: Int, private val listener: (Country) -> Unit): RecyclerView.Adapter<CountryRecyclerViewAdapter.CountryViewHolder>(){

    var countries: List<Country> = emptyList()

    inner class CountryViewHolder(private val binding: CardviewCountryBinding, private val listener: (Country) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.apply {
                tvCountryName.text = country.name
                tvShortName.text = country.short_name
                tvPhoneCode.text = country.phone_code.toString()

                btnShowMap.setOnClickListener { listener(country) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CardviewCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    fun loadCountries(countries: List<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int { return countries.size }
    override fun getItemViewType(position: Int): Int { return getLayoutIdForPosition() }
    private fun getLayoutIdForPosition(): Int { return resource }
}