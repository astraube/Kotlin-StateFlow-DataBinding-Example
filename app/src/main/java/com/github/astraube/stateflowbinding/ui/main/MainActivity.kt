package com.github.astraube.stateflowbinding.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.github.astraube.stateflowbinding.common.extensions.viewBinding
import com.github.astraube.stateflowbinding.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindToViewModel()

        initUI(savedInstanceState)
    }

    private fun bindToViewModel() {
        /**
         * Executable only when the xml layout is of type <layout></layout>
         *
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
         */
    }

    private fun initUI(savedInstanceState: Bundle?) {

    }
}