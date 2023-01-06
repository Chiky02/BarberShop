package com.example.nave

import android.os.Bundle
import android.view.Menu
import android.view.WindowId
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.nave.databinding.ActivityMain3Binding

class Main3Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain3.toolbar)

        binding.appBarMain3.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main3)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(

                /*esta configuracion hace que la nueva seccion se vea como una inicio y no
                *aparezca una flecha de ir atrás
                 */

                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.Deudas,R.id.fragmentPrueba
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main3, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main3)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}