package co.dev.yovany.intergrupoandroidtest.country.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.dev.yovany.intergrupoandroidtest.country.model.Country
import co.dev.yovany.intergrupoandroidtest.databinding.CardviewCountryBinding

class CountryRecyclerViewAdapter(private val resource: Int): RecyclerView.Adapter<CountryRecyclerViewAdapter.CountryViewHolder>(){

    var countries: List<Country> = emptyList()

    inner class CountryViewHolder(private val binding: CardviewCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CardviewCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int { return countries.size }
    override fun getItemViewType(position: Int): Int { return getLayoutIdForPosition() }
    private fun getLayoutIdForPosition(): Int { return resource }
}