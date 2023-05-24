package com.example.spanish.presentation.finish

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.spanish.R
import com.example.spanish.databinding.FragmentFinishBinding

class FinishFragment: Fragment(R.layout.fragment_finish) {

    private lateinit var binding: FragmentFinishBinding
    private val viewModel: FinishViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_finish, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFinishBinding.bind(view)
        val countAnswer = arguments?.getInt("countAnswer", 0)?: 0
        val countTask = arguments?.getInt("countTasks", 5)?: 5
        binding.countAnswerAndTasks.text = "$countAnswer/$countTask"
        listener()
    }

    private fun listener() {
        binding.btFinish.setOnClickListener {
            findNavController().navigate(R.id.mainMenuFragment, null, navOptions {
                popUpTo(R.id.mainMenuFragment) {
                    inclusive = true
                }
            })
        }
    }

}