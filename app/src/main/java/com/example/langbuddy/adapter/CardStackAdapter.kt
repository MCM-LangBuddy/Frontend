package com.example.langbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.langbuddy.R
import com.example.langbuddy.model.User


class CardStackAdapter(
    private var users: List<User> = emptyList(),
    private val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    private var viewHolder: ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_spot,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("uuuuuuuuuuuuuuuu")
        val user: User = users[position]
        holder.name.text = user.firstName
        holder.languages.text = user.languagesFormatted()
        holder.user = user
        Glide.with(holder.image)
            .load(user.profilePictureUrl)
            .placeholder(R.drawable.placeholder_avatar)
            .into(holder.image)
        holder.itemView.setOnClickListener(onClickListener)
        viewHolder = holder
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setProducts(spots: List<User>) {
        this.users = spots
    }

    fun getProducts(): List<User> {
        return users
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var user: User? = null
        val name: TextView = view.findViewById(R.id.item_name)
        var languages: TextView = view.findViewById(R.id.item_price)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}