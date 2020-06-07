package com.example.langbuddy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.langbuddy.R
import com.example.langbuddy.activity.MainActivity
import com.example.langbuddy.adapter.CustomAdapter
import com.example.langbuddy.model.User


class MatchesFragment(
    val matches: List<User>
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: ListView = view.findViewById(R.id.list)
        if (matches.isNotEmpty()) {
            val customAdapter = CustomAdapter(
                activity!!.applicationContext,
                matches
            )
            list.adapter = customAdapter
            list.setOnItemClickListener { _, _, position, _ ->
                val user = customAdapter.getItem(position)
                val a = activity as MainActivity
                if (user != null) {
                    a.showProductDetailFragment(user)
                }
            }
        }
    }
}
