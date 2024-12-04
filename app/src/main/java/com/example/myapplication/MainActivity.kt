package com.example.myapplication
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceControl.Transaction
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawer_layout)
        navigationView=findViewById(R.id.navigation)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        laodFragment(HomeFragment())

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    laodFragment(HomeFragment())
                    true
                }

                R.id.breakfast->{
                    laodFragment(BreakfastFragment())
                    true
                }

                R.id.lunch->{
                    laodFragment(LunchFragment())
                    true
                }

                R.id.dinner->{
                    laodFragment(DinnerFragment())
                    true
                }

                else -> {
                    laodFragment(BreakfastFragment())
                    true
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else super.onOptionsItemSelected(item)
    }

    private fun laodFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
        drawerLayout.closeDrawers()
    }
}
