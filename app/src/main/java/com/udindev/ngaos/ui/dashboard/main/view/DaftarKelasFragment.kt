package com.udindev.ngaos.ui.dashboard.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.udindev.ngaos.R
import com.udindev.ngaos.data.model.Kelas
import com.udindev.ngaos.databinding.FragmentDaftarKelasBinding
import com.udindev.ngaos.ui.dashboard.main.adapter.DaftarKelasAdapter

class DaftarKelasFragment : Fragment() {

    private lateinit var binding : FragmentDaftarKelasBinding
    private lateinit var adapter : DaftarKelasAdapter
    private var listKelas : ArrayList<Kelas> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDaftarKelasBinding.inflate(inflater, container, false)

        binding.rvDaftarKelas.layoutManager = LinearLayoutManager(context)
        binding.rvDaftarKelas.setHasFixedSize(true)
        adapter = DaftarKelasAdapter(requireActivity())
        adapter.notifyDataSetChanged()
        binding.rvDaftarKelas.adapter = adapter
        addList()
        return binding.root
    }

    private fun addList(){
        var kelas1 = Kelas("1", "Hafalan Surat Al-Ikhlas", "Pak Robbani", "21 April 2021",
        "20 Mei 2021", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
                "Cursus maecenas vitae lobortis ut diam magna turpis. \n" +
                "Enim, ante in ut tristique neque. Amet bibendum pulvinar \n" +
                "varius ipsum sem iaculis. Consectetur neque vitae elit susp\n" +
                "endisse. Ultricies placerat sit felis velit, odio venenatis et, \n" +
                "consectetu", "https://images.unsplash.com/photo-1560740365-a52c20ade8fd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80")
        listKelas.add(kelas1)

        adapter.data = listKelas
    }

}