package com.example.myapplication

import ImageSliderAdapter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var imageSlider: ViewPager2
    private lateinit var welcomePage: View
    private val handler = Handler()
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Drawer setup
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Image slider setup
        imageSlider = findViewById(R.id.imageSlider)
        welcomePage = findViewById(R.id.welcomePage)
        setupImageSlider()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Log.i("Pantry", "Pantry was opened")
                    loadFragment(HomeFragment())
                    true
                }
                R.id.breakfast -> {
                    Log.i("Breakfast", "Looking at breakfast")
                    loadFragment(BreakfastFragment())
                    true
                }
                R.id.lunch -> {
                    Log.i("Lunch", "Looking at lunch")
                    loadFragment(LunchFragment())
                    true
                }
                R.id.dinner -> {
                    Log.i("Dinner", "Looking at dinner")
                    loadFragment(DinnerFragment())
                    true
                }
                else -> {
                    loadFragment(HomeFragment())
                    true
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: Fragment) {
        // Hide the welcome page when switching fragments
        welcomePage.visibility = View.GONE

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
        drawerLayout.closeDrawers()
    }

    private fun setupImageSlider() {
        val images = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
        )

        val adapter = ImageSliderAdapter(images)
        imageSlider.adapter = adapter

        val runnable = object : Runnable {
            override fun run() {
                currentPage = (currentPage + 1) % images.size
                imageSlider.currentItem = currentPage
                handler.postDelayed(this, 3000) // Slide every 3 seconds
            }
        }
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
