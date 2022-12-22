package com.example.nave.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nave.R
import com.example.nave.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val barber=resources.getStringArray(R.array.Barbers)
        val arrayAdapter= ArrayAdapter(requireContext(), R.layout.dropdown_item,barber)
        binding.autoCompleteTextView5.setAdapter(arrayAdapter)



        binding.button.setOnClickListener{
            var barber2x=binding.autoCompleteTextView5.text




            val xd= AlertDialog.Builder(context)
                .setMessage("Hola como vas $barber2x XDXD" )
                .setTitle("SALUDO")

                .show()
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))




        }





        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}