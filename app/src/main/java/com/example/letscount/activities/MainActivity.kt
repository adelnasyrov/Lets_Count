package com.example.letscount.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.letscount.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var navController: NavController
    private var exitButton: ImageButton? = null
    private var toolbar: Toolbar? = null
    private var appBarLayout: AppBarLayout? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        exitButton = findViewById(R.id.exit_button)
        toolbar?.title = "Plus"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_plus)
        drawerLayout = findViewById(R.id.drawer)
        appBarLayout = findViewById(R.id.app_bar_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.itemIconTintList = null
        bottomNavigationView = findViewById(R.id.bottom)
        navigationView.setCheckedItem(R.id.plus)
        bottomNavigationView?.itemIconTintList = null
        navController = findNavController(R.id.nav_host_fragment_content_main)
        NavigationUI.setupWithNavController(navigationView, navController)
        var currentItem = "plus"
        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    exitButton?.setImageResource(R.drawable.exit_plus)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_plus)
                    if (currentItem == "minus")
                        navController.navigate(R.id.action_minus_to_plus)
                    if (currentItem == "multiplication")
                        navController.navigate(R.id.action_multiplication_to_plus)
                    if (currentItem == "division")
                        navController.navigate(R.id.action_division_to_plus)
                    currentItem = "plus"
                    toolbar?.title = "Plus"
                    true
                }
                R.id.item_2 -> {
                    exitButton?.setImageResource(R.drawable.exit_minus)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_minus)
                    if (currentItem == "plus")
                        navController.navigate(R.id.action_plus_to_minus)
                    if (currentItem == "multiplication")
                        navController.navigate(R.id.action_multiplication_to_minus)
                    if (currentItem == "division")
                        navController.navigate(R.id.action_division_to_minus)
                    currentItem = "minus"
                    toolbar?.title = "Minus"
                    true
                }
                R.id.item_3 -> {
                    exitButton?.setImageResource(R.drawable.exit_multiplication)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_multiplication)
                    if (currentItem == "plus")
                        navController.navigate(R.id.action_plus_to_multiplication)
                    if (currentItem == "minus")
                        navController.navigate(R.id.action_minus_to_multiplication)
                    if (currentItem == "division")
                        navController.navigate(R.id.action_division_to_multiplication)
                    currentItem = "multiplication"
                    toolbar?.title = "Multiplication"
                    true
                }
                R.id.item_4 -> {
                    if (currentItem == "plus")
                        navController.navigate(R.id.action_plus_to_division)
                    if (currentItem == "minus")
                        navController.navigate(R.id.action_minus_to_division)
                    if (currentItem == "multiplication")
                        navController.navigate(R.id.action_multiplication_to_division)
                    currentItem = "division"
                    toolbar?.title = "Division"
                    true
                }
                else -> false
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.plus -> showBottomNav()
                R.id.minus -> showBottomNav()
                R.id.multiplication -> showBottomNav()
                R.id.division -> showBottomNav()
                R.id.plus_game_1 -> hideBottomNav()
                R.id.plus_game_2 -> hideBottomNav()
                R.id.starter -> hideEverything()
                R.id.minus_game_1 -> hideBottomNav()
                R.id.minus_game_2 -> hideBottomNav()
            }
        }

    }

    private fun showBottomNav() {
        appBarLayout?.visibility = View.VISIBLE
        toolbar?.visibility = View.VISIBLE
        bottomNavigationView?.visibility = View.VISIBLE
        exitButton?.visibility = View.GONE
    }

    private fun hideBottomNav() {
        appBarLayout?.visibility = View.VISIBLE
        toolbar?.visibility = View.VISIBLE
        exitButton?.visibility = View.VISIBLE
        bottomNavigationView?.visibility = View.GONE
    }

    private fun hideEverything() {
        appBarLayout?.visibility = View.GONE
        exitButton?.visibility = View.GONE
        bottomNavigationView?.visibility = View.GONE
        toolbar?.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout?.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        currentObject = null
        super.onBackPressed()
    }

    override fun onDestroy() {
        currentObject = null
        super.onDestroy()
    }

    companion object {
        var currentObject: CountDownTimer? = null
    }

}