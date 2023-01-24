package com.example.nave.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nave.databinding.FragmentSlideshowBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    val db = Firebase.firestore

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.buttonprest.setOnClickListener {
            information()
        }



        return root
    }


    public fun pushGasto(descripcion: String,currentDate: String,currentTime: String,valor: String){


        val cortes= hashMapOf(
            "Valor" to valor,
            "Fecha" to currentDate,
            "Hora" to currentTime,
            "Descripción" to descripcion
        )

        // Add a new document with a generated ID
        db.collection("Prestamos")
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
        val descripcion=binding.descriptText.text.toString()
        val valor=binding.editTextNumber.text.toString()

        if(descripcion.toString()==""){
            Toast.makeText(context, "Debes digitar descripcion del prestamos", Toast.LENGTH_LONG).show()
        }
        else if(valor.toString()=="") {
            Toast.makeText(context, "Debes digitar el valor del prestamo", Toast.LENGTH_LONG).show()

        }
        else{

            val xd= AlertDialog.Builder(context)
                .setMessage("¿Deseas registras el prestamo por $valor?")
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    Toast.makeText(context, "Has elegido aceptar", Toast.LENGTH_LONG).show()

                    pushGasto(descripcion.toString(),currentDate,currentTime,valor.toString())
                }

                .setNegativeButton(android.R.string.cancel) { dialog, which ->
                    Toast.makeText(context, "Accion cancelada", Toast.LENGTH_LONG).show()
                }
            xd.show()
            //variable tipo hashmap para almacenar los datos a enviar al firebase
            //limpiando espacios de texto
            binding.descriptText.setText("")
            binding.editTextNumber.setText("")
        }

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}