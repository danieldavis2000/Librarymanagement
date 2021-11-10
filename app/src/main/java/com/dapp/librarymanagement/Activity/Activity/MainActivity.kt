package com.dapp.librarymanagement.Activity.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.dapp.librarymanagement.Activity.Fragment.AboutappFragment
import com.dapp.librarymanagement.Activity.Fragment.DashboardFragment
import com.dapp.librarymanagement.Activity.Fragment.FavouritesFragment

import com.dapp.librarymanagement.Activity.Fragment.ProfileFragment
import com.dapp.librarymanagement.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawerlayout : DrawerLayout
    lateinit var coordinatorlayout : CoordinatorLayout
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    lateinit var frame : FrameLayout
    lateinit var navigator : NavigationView
    lateinit var loginflag : SharedPreferences
    var previousMenuItem : MenuItem? = null
            override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            drawerlayout = findViewById(R.id.drawerlayout)
            coordinatorlayout = findViewById(R.id.coordinatorlayout)
//            toolbar = findViewById(R.id.toolbar)
            frame = findViewById(R.id.frame)
            navigator = findViewById(R.id.navigationview)
                loginflag = getSharedPreferences("Loginflag", Context.MODE_PRIVATE)
//            setUpToolbar()
            openDashboard()
            val actionbardrawertoggle = ActionBarDrawerToggle(this@MainActivity,drawerlayout,
                R.string.open_drawer,
                R.string.close_drawer
            )
            drawerlayout.addDrawerListener(actionbardrawertoggle)
            actionbardrawertoggle.syncState()
            navigator.setNavigationItemSelectedListener{
                if(previousMenuItem != null) {
                    previousMenuItem?.isChecked = false
                }
                it.isCheckable = true
                it.isChecked = true
                previousMenuItem = it
                when(it.itemId){
                    R.id.dashboard ->{
                        openDashboard()
                        drawerlayout.closeDrawers()

                    }
                    R.id.favourites ->{
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                FavouritesFragment()
                            )

                            .commit()
                        drawerlayout.closeDrawers()
                        supportActionBar?.title="Favourites"
                    }
                    R.id.profile ->{
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                ProfileFragment()
                            )

                            .commit()
                        drawerlayout.closeDrawers()
                        supportActionBar?.title="Profile"
                    }
                    R.id.aboutapp ->{
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.frame,
                                AboutappFragment()
                            )

                            .commit()
                        drawerlayout.closeDrawers()
                        supportActionBar?.title="About App"
                    }
                    R.id.logout->{
                        loginflag.edit().putBoolean("isloggedin",false).apply()
                        val dialogue = AlertDialog.Builder(this@MainActivity)
            dialogue.setTitle("Logout")
            dialogue.setMessage("Want to logout ?")
            dialogue.setPositiveButton("yes") { text, listener ->

                ActivityCompat.finishAffinity(this@MainActivity)

            }
            dialogue.setNegativeButton("No") { text, listener ->
            }
            dialogue.create()
            dialogue.show()
                    }

                }
                return@setNavigationItemSelectedListener(true)

            }
        }
        fun setUpToolbar()
        {
            setSupportActionBar(toolbar)
            supportActionBar?.title = "Toolbar Title"
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if(id == android.R.id.home){
                drawerlayout.openDrawer(GravityCompat.START)
            }
            return super.onOptionsItemSelected(item)
        }
        fun openDashboard() {
            val dash = DashboardFragment()
            val opendash = supportFragmentManager.beginTransaction()
            opendash.replace(R.id.frame,dash)
            opendash.commit()
            supportActionBar?.title="Dashboard"
            navigator.setCheckedItem(R.id.dashboard)

        }

        override fun onBackPressed() {

            val frag = supportFragmentManager.findFragmentById(R.id.frame)
            when(frag){
                !is DashboardFragment -> openDashboard()
                else ->{super.onBackPressed()
                    }
            }

        }


}