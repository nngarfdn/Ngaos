package com.udindev.ngaos.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.udindev.ngaos.R
import com.udindev.ngaos.data.model.Kelas
import com.udindev.ngaos.databinding.ActivitySearchBinding
import com.udindev.ngaos.ui.dashboard.main.adapter.DaftarKelasAdapter

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var adapter : DaftarKelasAdapter
    private var listKelas : ArrayList<Kelas> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        adapter = DaftarKelasAdapter(this)

        binding.rvDaftarKelas.layoutManager = LinearLayoutManager(this)
        binding.rvDaftarKelas.setHasFixedSize(true)
        adapter = DaftarKelasAdapter(this)
        adapter.notifyDataSetChanged()
        binding.rvDaftarKelas.adapter = adapter
        addList()

        binding.editText.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun addList(){
        var kelas1 = Kelas("1", "Hafalan Surat Al-Ikhlas", "Pak Robbani", "21 April 2021",
            "20 Mei 2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
                    "Cursus maecenas vitae lobortis ut diam magna turpis. \n" +
                    "Enim, ante in ut tristique neque. Amet bibendum pulvinar \n" +
                    "varius ipsum sem iaculis. Consectetur neque vitae elit susp\n" +
                    "endisse. Ultricies placerat sit felis velit, odio venenatis et, \n" +
                    "consectetu", "https://images.unsplash.com/photo-1560740365-a52c20ade8fd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80")

        var kelas2 = Kelas("2", "Belajar Iqro 6", "Mas Nasmi", "21 April 2021",
            "20 Mei 2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
                    "Cursus maecenas vitae lobortis ut diam magna turpis. \n" +
                    "Enim, ante in ut tristique neque. Amet bibendum pulvinar \n" +
                    "varius ipsum sem iaculis. Consectetur neque vitae elit susp\n" +
                    "endisse. Ultricies placerat sit felis velit, odio venenatis et, \n" +
                    "consectetu", "https://images.unsplash.com/photo-1560740365-a52c20ade8fd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80")

        listKelas.add(kelas1)
        listKelas.add(kelas2)

        adapter.data = listKelas
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}