package com.example.timernetic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView

class Dailygoal : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_goal)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item ->
            // Handle navigation item clicks here
            when (item.itemId) {
                R.id.nav_option1 -> {
                    // option 1 click
                    val navController = findNavController(R.id.nav_host_fragment)
                    navController.navigate(R.id.homeFragment)
                }
                R.id.nav_option2 -> {
                    val intent = Intent(this, Dailygoal::class.java)
                    startActivity(intent)
                }
                R.id.nav_option3 -> {
                    // Handle option 3 click
                    val intent = Intent(this, TimesheetView::class.java)
                    startActivity(intent)
                }
                R.id.nav_option4 -> {
                    // Handle option 4 click

                }
                R.id.nav_option5 -> {
                    // Handle option 5 click
                    val intent = Intent(this, MainMenu::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
