package com.example.practiceapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.practiceapp.databinding.ActivityMainBinding
import com.example.practiceapp.util.AppPrefsStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    @Inject lateinit var appPrefsStorage: AppPrefsStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
        }

        appPrefsStorage.isDarkTheme.asLiveData().observe(this) { isDark ->
            Timber.d("isDarkMode = $isDark")
            setDefaultNightMode(if (isDark) MODE_NIGHT_YES else MODE_NIGHT_NO)

        }

        binding.btnDark.setOnClickListener {
            lifecycleScope.launch {
                val isDark = appPrefsStorage.isDarkTheme.first()
                appPrefsStorage.setDarkTheme(!isDark)
            }
        }
    }
}