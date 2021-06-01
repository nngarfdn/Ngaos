package com.udindev.ngaos.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanang.retrocoro.ui.base.ViewModelFactory
import com.udindev.ngaos.R
import com.udindev.ngaos.api.ApiHelper
import com.udindev.ngaos.api.RetrofitBuilder
import com.udindev.ngaos.data.model.Kelas
import com.udindev.ngaos.data.response.kelas.Data
import com.udindev.ngaos.databinding.ActivitySearchBinding
import com.udindev.ngaos.databinding.FragmentDaftarKelasBinding
import com.udindev.ngaos.ui.dashboard.main.adapter.DaftarKelasAdapter
import com.udindev.ngaos.ui.dashboard.main.view.KelasViewModel
import com.udindev.ngaos.utils.Status

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var adapter : DaftarKelasAdapter
    private lateinit var viewModel: KelasViewModel
    private var listKelas : ArrayList<Kelas> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.rvDaftarKelas.layoutManager = LinearLayoutManager(this)
        binding.rvDaftarKelas.setHasFixedSize(true)
        adapter = DaftarKelasAdapter(this)
        adapter.notifyDataSetChanged()
        binding.rvDaftarKelas.adapter = adapter

        setupViewModel()
        setupObservers()

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

//    private fun addList(){
//        var kelas1 = Kelas("1", "Hafalan Surat Al-Ikhlas", "Pak Robbani", "21 April 2021",
//            "20 Mei 2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
//                    "Cursus maecenas vitae lobortis ut diam magna turpis. \n" +
//                    "Enim, ante in ut tristique neque. Amet bibendum pulvinar \n" +
//                    "varius ipsum sem iaculis. Consectetur neque vitae elit susp\n" +
//                    "endisse. Ultricies placerat sit felis velit, odio venenatis et, \n" +
//                    "consectetu", "https://images.unsplash.com/photo-1560740365-a52c20ade8fd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80")
//
//        var kelas2 = Kelas("2", "Belajar Iqro 6", "Mas Nasmi", "21 April 2021",
//            "20 Mei 2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
//                    "Cursus maecenas vitae lobortis ut diam magna turpis. \n" +
//                    "Enim, ante in ut tristique neque. Amet bibendum pulvinar \n" +
//                    "varius ipsum sem iaculis. Consectetur neque vitae elit susp\n" +
//                    "endisse. Ultricies placerat sit felis velit, odio venenatis et, \n" +
//                    "consectetu", "https://images.unsplash.com/photo-1560740365-a52c20ade8fd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80")
//
//        listKelas.add(kelas1)
//        listKelas.add(kelas2)
//
//        adapter.data = listKelas
//    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiServiceKelas))
        ).get(KelasViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getAllKelas().observe( this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { kelas ->
                            binding.progressBar.visibility = View.INVISIBLE
                            adapter = DaftarKelasAdapter(this)
                            adapter.notifyDataSetChanged()
                            adapter.data = kelas.data as java.util.ArrayList<Data>?
                            binding.rvDaftarKelas.adapter = adapter


                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}