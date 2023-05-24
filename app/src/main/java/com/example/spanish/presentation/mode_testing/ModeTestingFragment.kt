package com.example.spanish.presentation.mode_testing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.spanish.R
import com.example.spanish.databinding.FragmentModeTestingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModeTestingFragment : Fragment() {

    private lateinit var viewModel: ModeTestingViewModel
    private lateinit var binding: FragmentModeTestingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mode_testing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ModeTestingViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentModeTestingBinding.bind(view)
        binding.run {
            buttonTest.setOnClickListener {
                nextStep(null)
            }
            buttonTrening.setOnClickListener {
                nextStep(bundleOf("skipCountTask" to true))
            }
        }
    }

    private fun nextStep(bundle: Bundle?) {
        findNavController().navigate(R.id.chooseModeTestingFragment,bundle)
    }
}