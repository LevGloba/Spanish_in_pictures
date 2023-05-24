package com.example.spanish.presentation.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.spanish.R
import com.example.spanish.di.SingltonObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAuth()

    }

    private fun checkAuth() {
        Log.e(null,"auth : ${SingltonObject.auth.currentUser}")
        if (SingltonObject.auth.currentUser != null) {
            findNavController().navigate(R.id.main_host, null, navOptions {
                popUpTo(R.id.splashFragment) {
                    inclusive = true
                }
            })
        }
        else {
            findNavController().navigate(R.id.authFragment, null, navOptions {
                popUpTo(R.id.splashFragment) {
                    inclusive = true
                }
            })
        }
    }

}