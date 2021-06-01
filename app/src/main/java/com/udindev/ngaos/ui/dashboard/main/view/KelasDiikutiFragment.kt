package com.udindev.ngaos.ui.dashboard.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanang.retrocoro.ui.base.ViewModelFactory
import com.udindev.ngaos.R
import com.udindev.ngaos.api.ApiHelper
import com.udindev.ngaos.api.RetrofitBuilder
import com.udindev.ngaos.data.response.diikuti.Kela
import com.udindev.ngaos.data.response.kelas.Data
import com.udindev.ngaos.databinding.FragmentKelasDiikutiBinding
import com.udindev.ngaos.ui.dashboard.main.adapter.DaftarDiikutiAdapter
import com.udindev.ngaos.ui.dashboard.main.adapter.DaftarKelasAdapter
import com.udindev.ngaos.utils.Status

class KelasDiikutiFragment : Fragment() {

    private lateinit var binding : FragmentKelasDiikutiBinding
    private lateinit var adapter : DaftarDiikutiAdapter
    private lateinit var viewModel: KelasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentKelasDiikutiBinding.inflate(inflater, container, false)

        binding.rvDaftarKelas.layoutManager = LinearLayoutManager(context)
        binding.rvDaftarKelas.setHasFixedSize(true)
        adapter = DaftarDiikutiAdapter(requireActivity())
        adapter.notifyDataSetChanged()
        binding.rvDaftarKelas.adapter = adapter
        setupViewModel()
        setupObservers()

        return binding.root

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiServiceDiikuti))
        ).get(KelasViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getKelasDiikuti("mkM6mEgvobewNfmBBdjYDo2JExq1").observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { kelas ->
                            binding.progressBar.visibility = View.INVISIBLE
                            adapter = DaftarDiikutiAdapter(requireActivity())
                            adapter.notifyDataSetChanged()
                            adapter.data = kelas.data?.kelas as java.util.ArrayList<Kela>?
                            binding.rvDaftarKelas.adapter = adapter


                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Log.d("EROR", "setupObservers: ${it.message}")
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }


}