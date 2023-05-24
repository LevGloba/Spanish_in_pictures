package com.example.spanish.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.spanish.R
import com.example.spanish.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        onClickListener()
        observesListener()
    }

    private fun onClickListener() {
        binding.run {
            btSingUp.setOnClickListener {
                findNavController().navigate(R.id.registrationFragment)
            }
            btOk.setOnClickListener {
                viewModel.signInUserWithEmail(
                    email = enterEmail.text.toString(),
                    password = enterPassword.text.toString()
                )
            }
        }
    }

    private fun observesListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is AuthStatus.Error -> Toast.makeText(
                            requireContext(),
                            it.e,
                            Toast.LENGTH_LONG
                        ).show()
                        AuthStatus.NextStep -> openMainNav()
                    }
                }
            }
        }
    }

    private fun openMainNav() {
        findNavController().navigate(R.id.main_host, null, navOptions {
            popUpTo(R.id.authFragment) {
                inclusive = true
            }
        })
    }
}