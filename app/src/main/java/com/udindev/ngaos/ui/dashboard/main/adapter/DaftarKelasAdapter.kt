package com.udindev.ngaos.ui.dashboard.main.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udindev.ngaos.R
import com.udindev.ngaos.data.response.kelas.Data
import com.udindev.ngaos.ui.kelas.DetailActivity
import com.udindev.ngaos.utils.AppUtils.loadImageFromUrl
import java.util.*

class DaftarKelasAdapter(private val activity: Activity) : RecyclerView.Adapter<DaftarKelasAdapter.FavoriteViewHolder>(), Filterable {
    private val listItem = ArrayList<Data>()
    private val listItemFiltered = ArrayList<Data>()
    var data: ArrayList<Data>?
        get() = listItem
        set(listItem) {
            this.listItem.clear()
            this.listItem.addAll(listItem!!)
            listItemFiltered.clear()
            listItemFiltered.addAll(listItem)
            notifyDataSetChanged()
        }

    var countryFilterList = ArrayList<Data>()
    init {
        countryFilterList = data!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kelas, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = countryFilterList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_KELAS, item)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return countryFilterList.size
    }

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPhoto: ImageView = itemView.findViewById(R.id.img_kelas)
        private var tvName: TextView = itemView.findViewById(R.id.txt_nama_kelas)
        private var tv_pengajar : TextView = itemView.findViewById(R.id.textView2)
        fun bind(item: Data) {
            tvName.text = item.namaKelas
            tv_pengajar.text = item.deskripsiKelas
            loadImageFromUrl(imgPhoto, item.fotoKelas)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryFilterList = if (charSearch.isEmpty()) {
                    data!!
                } else {
                    val resultList = ArrayList<Data>()
                    for (row in data!!) {
                        if (row.namaKelas!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<Data>
                notifyDataSetChanged()
            }

        }
    }
}