package com.example.domainlogicandroidtest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domainlogicandroidtest.databinding.ItemUserBinding
import com.example.domainlogicandroidtest.ui.model.UserView

class UserAdapter(private val onUserClick: (UserView) -> Unit) : ListAdapter<UserView, UserAdapter.UserViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserView) {
            binding.usernameTextView.text = user.login
            binding.userIdTextView.text = user.id.toString()
            Glide.with(binding.avatarImageView.context)
                .load(user.avatarUrl)
                .into(binding.avatarImageView)

            binding.root.setOnClickListener {
                onUserClick(user)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserView>() {
        override fun areItemsTheSame(oldItem: UserView, newItem: UserView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserView, newItem: UserView): Boolean {
            return oldItem == newItem
        }
    }
}