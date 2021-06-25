package com.example.practiceapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.databinding.ItemUserInfoBinding
import com.example.practiceapp.model.UserInfo

class UserInfoAdapter: PagingDataAdapter<UserInfo, UserInfoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        return UserInfoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val userInfo = getItem(position) ?: return
        holder.bind(userInfo)
    }


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserInfo> =
            object : DiffUtil.ItemCallback<UserInfo>() {
                override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo) =
                    oldItem == newItem
            }
    }

}

class UserInfoViewHolder(private val binding: ItemUserInfoBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(userInfo: UserInfo) {
        binding.run {
            tvId.text = userInfo.id
            tvInfo.text = userInfo.name
        }
    }
    companion object {
        fun from(parent: ViewGroup): UserInfoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemUserInfoBinding.inflate(layoutInflater, parent, false)
            return UserInfoViewHolder(binding)
        }
    }
}