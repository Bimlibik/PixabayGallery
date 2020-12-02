package ru.bimlibik.pixabaygallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.bimlibik.pixabaygallery.R

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.fragment_container)

        appBarConfig = AppBarConfiguration(setOf(R.id.pictures_fragment), drawer_layout)
        setupActionBarWithNavController(navController, appBarConfig)

        nav_drawer.setupWithNavController(navController)
        nav_drawer.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_animals -> true
                R.id.menu_architecture -> true
                R.id.menu_music -> true
                R.id.menu_nature -> true
                R.id.menu_places -> true
            }
            drawer_layout.closeDrawers()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}