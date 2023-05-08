package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contentproviderapp.User
import com.example.contentproviderapp.databinding.ItemUserBinding

class UserAdapter(
    private val users : List<User>
):RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    inner class UserViewHolder(val binding : ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder.binding){
            tvEmail.text = users[position].email
            tvUsername.text = users[position].username
            tvPassword.text = users[position].password
        }
    }

}