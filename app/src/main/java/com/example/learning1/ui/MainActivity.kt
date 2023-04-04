package com.example.learning11.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.learning1.R
import com.example.learning1.ui.AddNotePage.AddNoteFragment
import com.example.learning1.ui.BottomNavigation.BottomNavigationFragment
import com.example.learning1.ui.HomePage.HomeFragment
import com.example.learning1.utils.HandleClick
import com.example.learning1.utils.NavEvent
import com.example.learning1.utils.NavType
import com.example.learning1.utils.navigatioType
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
   lateinit var homeFragment: HomeFragment

    lateinit var navigationBar: BottomNavigationView
    lateinit var addNoteFragment: AddNoteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0,0)
        setContentView(R.layout.activity_main)
        NavEvent.navType = navigatioType.HOME
        homeFragment = HomeFragment()
        addfragment()
        NavEvent.navigation.navigationBar1 =findViewById(R.id.menu)
//        navigationBar =  findViewById(R.id.menu)
       // navigationBar.setItemSelected(2131362019,true,false)
        navigationBar = findViewById(R.id.menu)
        navigationBar.selectedItemId = R.id.home
        navigationBar.setOnItemSelectedListener { item ->
             when (item.itemId) {
                 R.id.home -> {
                     Log.e("selected id", "2 HIIIIIIIIIII")
                    NavEvent.navType = navigatioType.HOME
                    homeFragment = HomeFragment()
                    val transaction =
                        (this as AppCompatActivity).supportFragmentManager.beginTransaction()
                    transaction.replace(com.example.learning1.R.id.home, homeFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                }
                R.id.complete -> {
                    NavEvent.navType = navigatioType.COMPLETED

                    homeFragment = HomeFragment()
                    val transaction =
                        (this as AppCompatActivity).supportFragmentManager.beginTransaction()
                    transaction.replace(com.example.learning1.R.id.home, homeFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    Log.e("selected id", "2 HIIIIIIIIIII")
                }
                R.id.add -> {
                    Log.e("selected id", "2 HIIIIIIIIIII")
                    addNoteFragment = AddNoteFragment()
                    val transaction =
                        (this as AppCompatActivity).supportFragmentManager.beginTransaction()
                    transaction.replace(com.example.learning1.R.id.home, addNoteFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                else -> {}
            }
            true
        }

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // Back is pressed... Finishing the activity
//                homeFragment = HomeFragment()
//                val transaction = this@MainActivity.supportFragmentManager.beginTransaction()
//                transaction.replace(com.example.learning1.R.id.home, homeFragment)
//                transaction.addToBackStack(null)
//                transaction.commit()
//                NavEvent.navigation.navigationBar1.selectedItemId = R.id.home
//            }
//        })
    }


    fun addfragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.home,homeFragment)
            .commitAllowingStateLoss()
    }

//    fun onCancelClick() {
//        navigationBar.setItemSelected(2131362019,true,false )
//    }



}