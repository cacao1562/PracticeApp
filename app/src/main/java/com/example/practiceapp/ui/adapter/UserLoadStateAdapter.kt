package com.example.practiceapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.databinding.ItemUserStateBinding
import com.example.practiceapp.ui.main.UserInfoAdapter


class UserLoadStateAdapter(
    private val adapter: UserInfoAdapter
): LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.from(parent) {
            adapter.retry()
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}

class LoadStateViewHolder(
    private val binding: ItemUserStateBinding,
    private val retryCallback: () -> Unit
):
    RecyclerView.ViewHolder(binding.root) {

    private val retry = binding.btnRetry.also {
        it.setOnClickListener { retryCallback() }
    }

    fun bind(loadState: LoadState) {
        binding.pbLoading.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        binding.tvError.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        binding.tvError.text = (loadState as? LoadState.Error)?.error?.message
    }

    companion object {
        fun from(parent: ViewGroup, retryCallback: () -> Unit,
        ): LoadStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemUserStateBinding.inflate(layoutInflater, parent, false)
            return LoadStateViewHolder(binding, retryCallback)
        }
    }
}