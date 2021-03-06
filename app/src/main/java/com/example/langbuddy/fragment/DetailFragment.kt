package com.example.langbuddy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.langbuddy.R
import com.example.langbuddy.activity.MainActivity
import com.example.langbuddy.model.User
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment(val user: User) : Fragment(), View.OnScrollChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun showIndicator() {
        scrollIndicator.visibility = View.VISIBLE
    }

    private fun hideIndicator() {
        scrollIndicator.visibility = View.INVISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(detail_imageView)
            .load(user.profilePictureUrl)
            .placeholder(R.drawable.placeholder_avatar)
            .into(detail_imageView)
        detail_item_name.text = "" + user.firstName
        detail_item_price.text = "" + user.languagesFormatted()
        detail_item_detail.text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet." + user.userId
        detail_imageView.setOnClickListener {
            val a = activity as MainActivity
            a.gotBackToSwipeFragment()
        }
        scroll_view.setOnScrollChangeListener(this)
    }

    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (scrollY == 0) {
            showIndicator()
        } else {
            hideIndicator()
        }
    }
}
