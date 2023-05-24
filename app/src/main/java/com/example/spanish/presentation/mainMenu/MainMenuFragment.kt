package com.example.spanish.presentation.mainMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.spanish.R
import com.example.spanish.databinding.FragmentMainMenuBinding
import com.example.spanish.di.SingltonObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        binding.buttonAnswer.setOnClickListener {
            findNavController().navigate(R.id.chooseThemeFragment)
        }
        binding.logOut.setOnClickListener {
            SingltonObject.signOut()
            findNavController().navigate(R.id.authFragment,null, navOptions {
                popUpTo(R.id.mainMenuFragment) {
                    inclusive = true
                }
            })
        }
    }

}