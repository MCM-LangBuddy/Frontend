package com.example.langbuddy.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.langbuddy.R
import com.example.langbuddy.model.User
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



const val FILE_REQUEST = 2

class UserFragment : Fragment() {
    private val client = HttpClient()
    private var userId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newImageButton.setOnClickListener { selectImageInAlbum() }
        val sharedPref =
            activity!!.getSharedPreferences(R.string.preferences.toString(), Context.MODE_PRIVATE)
                ?: return
        userId = sharedPref.getInt(getString(R.string.user_id), -1)
        updateUserData()

    }

    private fun updateUserData() {
        GlobalScope.launch {
            val response =
                client.get<String>("https://mcm-langbuddy.herokuapp.com/api/auth/profile/" + userId)
            val user: User =
                Gson().fromJson(response, User::class.java)
            activity!!.runOnUiThread {
                nameTextfield.text = user.firstName
                languagesTextfield.text = user.languagesFormatted()
                Glide.with(userImage)
                    .load(user.profilePictureUrl)
                    .placeholder(R.drawable.placeholder_avatar)
                    .into(userImage)
            }
        }
    }

    private fun selectImageInAlbum() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), FILE_REQUEST);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Activity.RESULT_CANCELED -> {
                println("Errorrrrrr")
            }
            FILE_REQUEST -> {
                val uri = data?.data
                if (uri != null) {
                    createImageData(uri)
                    uploadImage()
                }
            }
        }
    }

    private var imageData: ByteArray? = null

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = activity!!.contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    private fun uploadImage() {
        imageData ?: return
        GlobalScope.launch {
            println("uploading doesn't work")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }
}
