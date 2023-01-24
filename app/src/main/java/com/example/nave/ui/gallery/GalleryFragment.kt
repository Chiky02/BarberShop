package com.example.nave.ui.gallery

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nave.databinding.FragmentGalleryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class GalleryFragment : Fragment() {

    //variable para conexion con base de datos
    private val db = Firebase.firestore
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.button2.setOnClickListener {
            information()
        }




        return root
    }

  private fun pushGasto(descripcion: String,currentDate: String,currentTime: String,valor: String){


        val cortes= hashMapOf(
            "Valor" to valor,
            "Fecha" to currentDate,
            "Hora" to currentTime,
            "Descripción" to descripcion
        )

        // Add a new document with a generated ID
        db.collection("Gastos")
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


    private fun information(){
        //obteniendo fecha y hora actual
        val currentDate = SimpleDateFormat("dd/M/yyyy").format(Date())
        val currentTime=  SimpleDateFormat("hh:mm:ss").format(Date())
        val descripcion=binding.editTextNumber2.text.toString()
        val valor=binding.textInputEdit.text.toString()

        if(descripcion ==""){
            Toast.makeText(context, "Debes digitar una descripción del gasto", Toast.LENGTH_LONG).show()
        }
        else if(valor=="") {
            Toast.makeText(context, "Debes digitar el valor del gasto", Toast.LENGTH_LONG).show()

        }
        else{

            val xd= AlertDialog.Builder(context)
                .setMessage("¿Deseas registrar el gasto por $valor?")
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    Toast.makeText(context, "Has elegido aceptar", Toast.LENGTH_LONG).show()

                    pushGasto(descripcion,currentDate,currentTime,valor)
                }

                .setNegativeButton(android.R.string.cancel) { dialog, which ->
                    Toast.makeText(context, "Accion cancelada", Toast.LENGTH_LONG).show()
                }

            xd.show()

            //variable tipo hashmap para almacenar los datos a enviar al firebase


            //limpiando espacios de texto

            binding.editTextNumber2.setText("")
            binding.textInputEdit.setText("")

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}