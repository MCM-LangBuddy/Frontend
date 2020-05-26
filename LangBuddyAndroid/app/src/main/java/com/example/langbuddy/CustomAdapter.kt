package com.example.langbuddy

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


class CustomAdapter(
    val context: Context,
    val matches: List<User>
) : ListAdapter {


    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun isEnabled(position: Int): Boolean {
        return true
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {}
    override fun unregisterDataSetObserver(observer: DataSetObserver?) {}
    override fun getCount(): Int {
        return matches.size
    }

    override fun getItem(position: Int): User? {
        return matches[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        val pair = matches[position]

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            convertView = layoutInflater.inflate(R.layout.list_row, null)

            val image: ImageView = convertView.findViewById(R.id.list_image)
            val title: TextView = convertView.findViewById(R.id.title)
            val price: TextView = convertView.findViewById(R.id.price)
            Glide.with(image)
                .load(matches[position].imageUrl)
                .into(image)
            title.text = matches[position].name
            price.text = matches[position].languages
        }
        return convertView
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return matches.size
    }

    override fun isEmpty(): Boolean {
        return false
    }
}