package com.example.practiceapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.practiceapp.databinding.ActivityMainBinding
import com.example.practiceapp.ui.adapter.UserLoadStateAdapter
import com.example.practiceapp.util.AppPrefsStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    @Inject lateinit var appPrefsStorage: AppPrefsStorage

    lateinit var mAdapter: UserInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
        }

        initDartMode()
        initAdapter()

    }

    private fun initDartMode() {
        appPrefsStorage.isDarkTheme.asLiveData().observe(this) { isDark ->
            Timber.d("isDarkMode = $isDark")
            setDefaultNightMode(if (isDark) MODE_NIGHT_YES else MODE_NIGHT_NO)

        }

        binding.btnDark.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val isDark = appPrefsStorage.isDarkTheme.first()
                appPrefsStorage.setDarkTheme(!isDark)
            }
        }
    }

    private fun initAdapter() {
        mAdapter = UserInfoAdapter()
        binding.rvMain.apply {
            setHasFixedSize(true)
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter(mAdapter),
                footer = UserLoadStateAdapter(mAdapter)
            )
        }

        binding.swipeRefresh.setOnRefreshListener {
            mAdapter.refresh()
        }

        lifecycleScope.launchWhenCreated {
            mAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.usersInfo.collectLatest {
                mAdapter.submitData(it)
            }
        }

    }
}