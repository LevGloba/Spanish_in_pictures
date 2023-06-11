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
import com.example.spanish.di.model.ChangeStringToolBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ModeTestingFragment : Fragment() {

    private lateinit var viewModel: ModeTestingViewModel
    private lateinit var binding: FragmentModeTestingBinding
    @Inject
    lateinit var changeStringToolBar: ChangeStringToolBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        changeStringToolBar.changeForTheme(requireActivity())
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
                changeStringToolBar.setMode(getString(R.string.test))
                changeStringToolBar.changeForMode(requireActivity())
                nextStep(null)
            }
            buttonTrening.setOnClickListener {
                changeStringToolBar.setMode(getString(R.string.Trening))
                changeStringToolBar.changeForMode(requireActivity())
                nextStep(bundleOf("skipCountTask" to true))
            }
        }
    }

    private fun nextStep(bundle: Bundle?) {
        findNavController().navigate(R.id.chooseModeTestingFragment,bundle)
    }
}