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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    var sum=0.0
    val db = Firebase.firestore
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onStart() {
        super.onStart()

        //con esto se carga el menu desplegable al empezar
        dropdown()
        //con esto se obtiene el saldo actual al empezar
        getInfo()
    }
    override fun onResume() {
        super.onResume()
        //con esto se carga el menu desplegable al empezar
        dropdown()
        //con esto se obtiene el saldo actual al empezar
        getInfo()
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



    public fun pushInfo(barber2x: String,currentDate: String,currentTime: String,pay: String){


        val cortes= hashMapOf(
            "Barbero" to barber2x.toString(),
            "Fecha" to currentDate.toString(),
            "Hora" to currentTime.toString(),
            "valor" to pay.toString()
        )

        // Add a new document with a generated ID
        db.collection("Cortes")
            //adiciona la coleccion de cortes acordde al hashmap
            .add(cortes)
            //en caso de exito muestra el siguiente mensaje
            .addOnSuccessListener { documentReference ->

                Toast.makeText(context, "Se ha registrado correctamente", Toast.LENGTH_LONG).show()
            }
            //en caso de error muestra el siguiente texto
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error: $e", Toast.LENGTH_LONG).show()
            }
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
                    .setMessage("Deseas registras el corte por un valor de $pay")
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        Toast.makeText(context, "Has elegiido aceptar", Toast.LENGTH_LONG).show()

                       pushInfo(barber2x.toString(),currentDate,currentTime,pay.toString())
                    }

                    .setNegativeButton(android.R.string.cancel) { dialog, which ->
                        Toast.makeText(context, "Accion cancelada", Toast.LENGTH_LONG).show()
                    }

                xd.show()

                //variable tipo hashmap para almacenar los datos a enviar al firebase


                //limpiando espacios de texto

                binding.autoCompleteTextView5.setText("")
                binding.autoCompleteTextView4.setText("")

                //sum=binding.textView6.text.toString().toInt()+pay.toString().toInt()
                //binding.textView6.text = sum.toString()

                getInfo()


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

    private fun getInfo(){
        var totalSum=0.0
        var p1=0
        var p2=0
        var p3=0

        db.collection("Cortes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val va=document.get("Barbero").toString()

                  totalSum+=document.get("valor").toString().toDouble()

                    when (va) {
                        "Peluquero 1" -> {
                            p1+=document.get("valor").toString().toInt()

                        }
                        "Peluquero 2" -> {
                            p2+=document.get("valor").toString().toInt()
                        }
                        else -> {
                            p3+=document.get("valor").toString().toInt()
                        }
                    }


                }
             /*   val xd= AlertDialog.Builder(context)
                    .setMessage("El peluquero 1 ha generado: $p1 \r " +
                            "El peluquero 2 ha generado: $p2 \n " +
                            " El peluquero 3 ha generado: $p3 \n" )
                    .setTitle("Reportes")
                xd.show()*/
                binding.textView6.text=totalSum.toString()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error: exception", Toast.LENGTH_LONG).show()
            }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}