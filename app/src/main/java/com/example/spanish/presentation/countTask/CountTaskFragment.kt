package com.example.spanish.presentation.countTask

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.spanish.R
import com.example.spanish.databinding.FragmentCountTaskBinding
import com.example.spanish.di.model.ChangeStringToolBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountTaskFragment : Fragment() {

    private lateinit var viewModel: CountTaskViewModel

    private lateinit var binding: FragmentCountTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_count_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CountTaskViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCountTaskBinding.bind(view)

        binding.start.setOnClickListener {
            checkCount()
        }
    }
    private fun checkCount() {
        binding.run {
            try {
                val countTask = editTextEnterCountTasks.text.toString().toInt()
                if (countTask in 1..20)
                    when(arguments?.getBoolean("img")) {
                        true -> findNavController().navigate(
                            R.id.testFragment, bundleOf(
                                "countTasks" to countTask,
                                "modeTesting" to false
                            )
                        )
                        else -> findNavController().navigate(
                            R.id.testMP3Fragment, bundleOf(
                                "countTasks" to countTask,
                                "modeTesting" to false
                            )
                        )
                    }
                else
                    editTextEnterCountTasks.error = "Введите цифру от 1 до 20"
            }catch (e: Throwable) {
                editTextEnterCountTasks.error = "Введите цифру от 1 до 20"
            }
        }
    }
}