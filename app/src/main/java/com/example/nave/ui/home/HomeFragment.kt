package com.example.nave.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nave.R
import com.example.nave.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var sum=0
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onStart() {
        super.onStart()
        dropdown()
        binding.textView6.text = sum.toString()
    }

    override fun onResume() {
        super.onResume()
        dropdown()
        binding.textView6.text = sum.toString()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button.setOnClickListener{
            information()


        }
        return root
    }
    fun information(){
        //obteniendo fecha y hora actual
        val currentDate = SimpleDateFormat("dd/M/yyyy").format(Date())
        val currentTime=  SimpleDateFormat("hh:mm:ss").format(Date())
        val barber2x=binding.autoCompleteTextView5.text
        val pay=binding.autoCompleteTextView4.text

            if(barber2x.toString()==""){

                Toast.makeText(context, "Debes elegir al barbero", Toast.LENGTH_LONG).show()
            }
            else if(pay.toString()=="") {
                Toast.makeText(context, "Debes elegir el precio de pago", Toast.LENGTH_LONG).show()

            }
            else{

                val xd= AlertDialog.Builder(context)
                .setMessage("Se ha registrado $barber2x la fecha es  $currentDate y la hora es $currentTime y el valor de la peluqueada es de $pay " )
                .setTitle("SALUDO")
                xd.show()
                binding.autoCompleteTextView5.setText("")
                binding.autoCompleteTextView4.setText("")
                sum=binding.textView6.text.toString().toInt()+pay.toString().toInt()
                binding.textView6.text = sum.toString()
            }




    }

    private fun dropdown(){


        //obteniendo las opciomes de peluquero
        val barber=resources.getStringArray(R.array.Barbers)
        val arrayAdapter= ArrayAdapter(requireContext(), R.layout.dropdown_item,barber)
        binding.autoCompleteTextView5.setAdapter(arrayAdapter)

        //obteniendo los precios

        val precios=resources.getStringArray(R.array.A)
        val arrayAdapte= ArrayAdapter(requireContext(), R.layout.dropdown_item,precios)
        binding.autoCompleteTextView4.setAdapter(arrayAdapte)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}