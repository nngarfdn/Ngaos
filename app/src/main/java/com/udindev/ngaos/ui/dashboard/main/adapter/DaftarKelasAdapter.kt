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
import com.udindev.ngaos.data.model.Kelas
import com.udindev.ngaos.ui.kelas.DetailActivity
import com.udindev.ngaos.utils.AppUtils.loadImageFromUrl
import java.util.*

class DaftarKelasAdapter(private val activity: Activity) : RecyclerView.Adapter<DaftarKelasAdapter.FavoriteViewHolder>(), Filterable {
    private val listItem = ArrayList<Kelas>()
    private val listItemFiltered = ArrayList<Kelas>()
    var data: ArrayList<Kelas>?
        get() = listItem
        set(listItem) {
            this.listItem.clear()
            this.listItem.addAll(listItem!!)
            listItemFiltered.clear()
            listItemFiltered.addAll(listItem)
            notifyDataSetChanged()
        }

    var countryFilterList = ArrayList<Kelas>()
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
//            intent.putExtra(BookDetailActivity.EXTRA_BOOK, item)
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
        fun bind(item: Kelas) {
            tvName.text = item.namaKelas
            loadImageFromUrl(imgPhoto, item.photo)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryFilterList = if (charSearch.isEmpty()) {
                    data!!
                } else {
                    val resultList = ArrayList<Kelas>()
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
                countryFilterList = results?.values as ArrayList<Kelas>
                notifyDataSetChanged()
            }

        }
    }
}