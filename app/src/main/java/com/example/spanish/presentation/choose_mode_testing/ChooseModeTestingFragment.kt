package com.example.spanish.presentation.choose_mode_testing

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.spanish.R
import com.example.spanish.databinding.FragmentChooseModeTestingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseModeTestingFragment : Fragment() {

    private lateinit var viewModel: ChooseModeTestingViewModel
    private lateinit var binding: FragmentChooseModeTestingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_mode_testing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChooseModeTestingViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseModeTestingBinding.bind(view)
        binding.buttonAnswer.setOnClickListener {
            if (arguments?.getBoolean("skipCountTask", false) == true)
                findNavController().navigate(R.id.testFragment)
            else
                findNavController().navigate(R.id.countTaskFragment)
        }
    }

}