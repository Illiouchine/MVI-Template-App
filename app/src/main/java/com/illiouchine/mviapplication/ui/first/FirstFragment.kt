package com.illiouchine.mviapplication.ui.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.illiouchine.mviapplication.R
import com.illiouchine.mviapplication.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val viewModel: FirstViewModel by viewModels()


    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = DataAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvDataList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.setOnClickListener { data: String ->
            viewModel.dispatchIntent(FirstContract.MainIntent.DataClick(data))
        }
        binding.rvDataList.adapter = adapter

        binding.buttonFirst.setOnClickListener {
            viewModel.dispatchIntent(FirstContract.MainIntent.ReloadDataClick)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                when (uiState.mainListState) {
                    is FirstContract.MainListState.Error -> {
                        binding.buttonFirst.visibility = View.VISIBLE
                        adapter.data = emptyList()
                        binding.textviewFirst.text =
                            getString(R.string.first_fragment_error_title)
                    }
                    FirstContract.MainListState.Loading -> {
                        binding.buttonFirst.visibility = View.GONE
                        adapter.data = emptyList()
                        binding.textviewFirst.text =
                            getString(R.string.first_fragment_loading_title)
                    }
                    is FirstContract.MainListState.Success -> {
                        binding.buttonFirst.visibility = View.VISIBLE
                        adapter.data = uiState.mainListState.data
                        binding.textviewFirst.text =
                            getString(R.string.first_fragment_success_title)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.event.collect { event ->
                when (event) {
                    is FirstContract.MainEvent.ShowToast -> {
                        Snackbar.make(
                            binding.root,
                            "${event.text} : should not re-appear on rotation",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is FirstContract.MainEvent.GoToDetail -> {
                        val dataClicked = event.dataClicked
                        Snackbar.make(binding.root, "data : $dataClicked", Snackbar.LENGTH_LONG)
                            .show()
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}