package com.internshala.foodrunner.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.foodrunner.R
import com.internshala.foodrunner.activity.fragment.FaqFragment
import com.internshala.foodrunner.fragment.FavouritesFragment
import com.internshala.foodrunner.fragment.HomeFragment
import com.internshala.foodrunner.fragment.ProfileFragment

class MiscActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var previousMenuItem: MenuItem? = null
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        setupToolbar()
        setupActionBarToggle()
        displayHome()


        navigationView.setNavigationItemSelectedListener { item: MenuItem ->


            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }


            item.isCheckable = true
            item.isChecked = true


            previousMenuItem = item


            val mPendingRunnable = Runnable { drawerLayout.closeDrawer(GravityCompat.START) }
            Handler().postDelayed(mPendingRunnable, 100)


            val fragmentTransaction = supportFragmentManager.beginTransaction()


            when (item.itemId) {


                R.id.home -> {
                    val homeFragment = HomeFragment()
                    fragmentTransaction.replace(R.id.frameLayout, homeFragment)
                    fragmentTransaction.commit()
                    supportActionBar?.title = "All Restaurants"
                }


                R.id.myProfile -> {
                    val profileFragment = ProfileFragment()
                    fragmentTransaction.replace(R.id.frameLayout, profileFragment)
                    fragmentTransaction.commit()
                    supportActionBar?.title = "My profile"
                }


                R.id.favoriteRestaurant -> {
                    val favFragment = FavouritesFragment()
                    fragmentTransaction.replace(R.id.frameLayout, favFragment)
                    fragmentTransaction.commit()
                    supportActionBar?.title = "Favorite Restaurants"
                }


                R.id.faqs -> {
                    val faqFragment = FaqFragment()
                    fragmentTransaction.replace(R.id.frameLayout, faqFragment)
                    fragmentTransaction.commit()
                    supportActionBar?.title = "Frequently Asked Questions"
                }


                R.id.logout -> {


                    val builder = AlertDialog.Builder(this@MiscActivity)
                    builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want exit?")
                        .setPositiveButton("Yes") { _, _ ->
                            ActivityCompat.finishAffinity(this)
                        }
                        .setNegativeButton("No") { _, _ ->
                            displayHome()
                        }
                        .create()
                        .show()

                }

            }
            return@setNavigationItemSelectedListener true
        }
    }


    private fun displayHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
        supportActionBar?.title = "All Restaurants"
        navigationView.setCheckedItem(R.id.home)
    }


    private fun setupActionBarToggle() {
        actionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
            ) {
                override fun onDrawerStateChanged(newState: Int) {
                    super.onDrawerStateChanged(newState)
                    val pendingRunnable = Runnable {
                        val inputMethodManager =
                            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    }
                    Handler().postDelayed(pendingRunnable, 50)
                }
            }


        drawerLayout.addDrawerListener(actionBarDrawerToggle)


        actionBarDrawerToggle.syncState()

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item?.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}



