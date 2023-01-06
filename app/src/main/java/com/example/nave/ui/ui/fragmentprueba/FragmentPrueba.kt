package com.example.nave.ui.ui.fragmentprueba

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nave.R

class FragmentPrueba : Fragment() {

    companion object {
        fun newInstance() = FragmentPrueba()
    }

    private lateinit var viewModel: FragmentPruebaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentPruebaViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fragment_prueba, container, false)
    }

}