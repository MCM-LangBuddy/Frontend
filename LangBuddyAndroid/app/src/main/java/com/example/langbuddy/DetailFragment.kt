package com.example.langbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment(val user: User) : Fragment(), View.OnScrollChangeListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
            .load(user.imageUrl)
            .into(detail_imageView)
        detail_item_name.text = "" + user.name
        detail_item_price.text = "" + user.languages
        detail_item_detail.text = "" + user.id
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
