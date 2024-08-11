package com.murad.palindrome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.palindrome.R
import com.murad.palindrome.data.remote.response.DataItem
import com.murad.palindrome.databinding.LayoutItemBinding

class UserAdapter(
    private val onItemClick: (DataItem) -> Unit
) : PagingDataAdapter<DataItem, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {
    class UserViewHolder(
        private val binding: LayoutItemBinding,
        private val onItemClick: (DataItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            val context = binding.root.context
            binding.textViewUsername.text = context.getString(R.string.user_full_name, data.firstName, data.lastName)
            binding.textViewEmail.text = data.email
            Glide.with(itemView)
                .load(data.avatar)
                .circleCrop()
                .into(binding.imageViewUserprofile)
            itemView.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onItemClick)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}