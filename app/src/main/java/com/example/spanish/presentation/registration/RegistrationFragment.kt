package com.example.spanish.presentation.registration

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.spanish.R
import com.example.spanish.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: FragmentRegistrationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
        objectListener()
        observesListener()
    }

    private fun objectListener() {
        binding.run {
            registerBtn.setOnClickListener {
                viewModel.createUserWithEmail(
                    email = emailET.text.toString(),
                    password = passwordET.text.toString(),
                    confirmPassword = passwordRepeatET.text.toString(),
                    lastName = lastnameET.text.toString(),
                    firstName = nameET.text.toString(),
                    emailTeacher = teachersEmailET.text.toString()
                )
            }
        }
    }

    private fun observesListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { it ->
                    when (it) {
                        is RegistrationStatus.Error -> Toast.makeText(
                            requireContext(),
                            it.e,
                            Toast.LENGTH_LONG
                        ).show()

                        RegistrationStatus.Return -> findNavController().popBackStack()
                    }
                }
            }
        }
    }
}