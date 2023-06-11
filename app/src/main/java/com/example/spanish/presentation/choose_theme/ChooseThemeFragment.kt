package com.example.spanish.presentation.choose_theme

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.spanish.R
import com.example.spanish.databinding.FragmentChooseThemeBinding
import com.example.spanish.di.model.ChangeStringToolBar
import com.example.spanish.di.model.Requests
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChooseThemeFragment : Fragment() {

    private lateinit var viewModel: ChooseThemeViewModel
    private lateinit var binding: FragmentChooseThemeBinding
    @Inject
    lateinit var changeStringToolBar: ChangeStringToolBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        changeStringToolBar.setTheme(getString(R.string.text_logo))
        changeStringToolBar.changeForTheme(requireActivity())
        return inflater.inflate(R.layout.fragment_choose_theme, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChooseThemeViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseThemeBinding.bind(view)
        binding.run {
            buttonFauna.setOnClickListener {
                viewModel.setRulse(Requests.FAUNA)
                changeStringToolBar(getString(R.string.fauna))
                nextStep()
            }
            buttonFlora.setOnClickListener {
                viewModel.setRulse(Requests.FLORA)
                changeStringToolBar(getString(R.string.FLORA))
                nextStep()
            }
        }
    }

    private fun changeStringToolBar(s: String) {
        changeStringToolBar.setTheme(s)
        changeStringToolBar.changeForTheme(requireActivity())
    }

    private fun nextStep() = findNavController().navigate(R.id.modeTestingFragment)

}