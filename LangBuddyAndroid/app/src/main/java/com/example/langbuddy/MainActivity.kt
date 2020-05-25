package com.example.langbuddy


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val swipeFragment = SwipeFragment()
    private var isSwipe = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, swipeFragment)
        fragmentTransaction.commit()
        getListButton.setOnClickListener {
            showListFragment()
        }
        getSwipeButton.setOnClickListener {
            showSwipeFragment()
        }
    }


    private fun showListFragment() {
        if (isSwipe) {
            val testUsers = mutableListOf<User>(
                User(
                    1321684,
                    "Martin Schneglberger",
                    "GER ENG DICK",
                    "asdf"
                ), User(
                    242345,
                    "Dominik Grüneis",
                    "GER ENG",
                    "asdf"
                )

            )

            val fragment = MatchesFragment(testUsers)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            isSwipe = false;
            //To change body of created functions use File | Settings | File Templates.
            list_active.setBackgroundColor(Color.parseColor("#ffffff"))
            swipe_active.setBackgroundColor(Color.parseColor("#6200ee"))
        }
    }

    private fun showSwipeFragment() {
        if (!isSwipe) {
            fragmentManager.popBackStackImmediate();
            isSwipe = true;
        }
        list_active.setBackgroundColor(Color.parseColor("#6200ee"))
        swipe_active.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    fun showProductDetailFragment(user: User) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DetailFragment(user!!)
        fragmentTransaction.add(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun gotBackToSwipeFragment() {
        getListButton.visibility = View.VISIBLE
        getSwipeButton.visibility = View.VISIBLE
        fragmentManager.popBackStackImmediate();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val weakActivity = WeakReference<Activity>(this)
    }
}

