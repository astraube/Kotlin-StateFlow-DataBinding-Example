package com.github.astraube.stateflowbinding.ui.names

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.astraube.stateflowbinding.R
import com.github.astraube.stateflowbinding.data.repository.MainRepository
import com.github.astraube.stateflowbinding.common.extensions.viewBinding
import com.github.astraube.stateflowbinding.data.source.isFailure
import com.github.astraube.stateflowbinding.data.source.statusMessage
import com.github.astraube.stateflowbinding.databinding.FragmentNamesBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NamesFragment : Fragment(R.layout.fragment_names) {

    private val binding by viewBinding(FragmentNamesBinding::bind)

    private val viewModel: NamesViewModel = NamesViewModel(MainRepository())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindToViewModel()

        initUI(savedInstanceState)
    }

    private fun bindToViewModel() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()

        lifecycle.addObserver(viewModel)

        viewModel.uiState.observe(viewLifecycleOwner) { resultSource ->
            Log.d("@@@", "uiState.observe: $resultSource")

            if (resultSource.isFailure) {
                Toast.makeText(this@NamesFragment.requireContext(), resultSource.statusMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initUI(savedInstanceState: Bundle?) {

    }
}