package com.example.langbuddy.activity


import android.Manifest
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.langbuddy.R
import com.example.langbuddy.fragment.DetailFragment
import com.example.langbuddy.fragment.MatchesFragment
import com.example.langbuddy.fragment.SwipeFragment
import com.example.langbuddy.fragment.UserFragment
import com.example.langbuddy.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.lang.reflect.Type

const val REQUEST_ID_MULTIPLE_PERMISSIONS = 7

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val swipeFragment = SwipeFragment()
    private var isMethod = 1
    private val client = HttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        getListButton.setOnClickListener {
            showListFragment()
        }
        getSwipeButton.setOnClickListener {
            showSwipeFragment()
        }
        getUserButton.setOnClickListener {
            showUserFragment()
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        val wtite = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val read = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


    private fun showListFragment() {
        if (isMethod!=2) {
            val sharedPref =
                getSharedPreferences(R.string.preferences.toString(), Context.MODE_PRIVATE)
                    ?: return
            val userId = sharedPref.getInt(getString(R.string.user_id), -1)
            GlobalScope.launch {
                val response =
                    client.get<String>("https://mcm-langbuddy.herokuapp.com/api/matching/matches/$userId")
                val listType: Type = object : TypeToken<ArrayList<User?>?>() {}.type
                val userList: List<User> =
                    Gson().fromJson(response, listType)


                val fragment =
                    MatchesFragment(userList)
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.fragment_container, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                isMethod = 2;
                //To change body of created functions use File | Settings | File Templates.
                list_active.setBackgroundColor(Color.parseColor("#ffffff"))
                swipe_active.setBackgroundColor(Color.parseColor("#6200ee"))
                user_active.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }
    }

    private fun showUserFragment() {
        if (isMethod!=0) {
            val fragment =
                UserFragment()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            isMethod = 0;
            //To change body of created functions use File | Settings | File Templates.
            list_active.setBackgroundColor(Color.parseColor("#ffffff"))
            swipe_active.setBackgroundColor(Color.parseColor("#ffffff"))
            user_active.setBackgroundColor(Color.parseColor("#6200ee"))
        }
}

private fun showSwipeFragment() {
    if (isMethod!=1) {
        while (fragment_container !is SwipeFragment) {
            fragmentManager.popBackStackImmediate()
        }
        isMethod = 1;
    }
    list_active.setBackgroundColor(Color.parseColor("#6200ee"))
    swipe_active.setBackgroundColor(Color.parseColor("#ffffff"))
    user_active.setBackgroundColor(Color.parseColor("#ffffff"))
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
}
}

