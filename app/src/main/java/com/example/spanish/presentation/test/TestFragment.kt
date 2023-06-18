package com.example.spanish.presentation.test

import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.spanish.R
import com.example.spanish.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.properties.Delegates

@AndroidEntryPoint
class TestFragment : Fragment() {


    private val viewModel: TestViewModel by viewModels()
    private lateinit var binding: FragmentTestBinding
    private  var countTask: Int = 0
    private var modeTesting: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countTask = arguments?.getInt("countTasks", 5) ?: 5
        modeTesting = arguments?.getBoolean("modeTesting", false)?: false
        Log.e(null, "countTask = $countTask\nmodeTesting = $modeTesting")
        binding = FragmentTestBinding.bind(view)
        binding.enterAnswer.imeHintLocales = LocaleList(Locale("es", "ES"))
        viewModel.setCountTaskAndModeTesting(countTask, modeTesting)
        observesListenerStateTestFragment()
        listener()
    }

    private fun observesListenerStateTestFragment() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.test.collect {
                    when (it) {
                        is Test.Error -> Toast.makeText(
                            requireContext(),
                            it.e,
                            Toast.LENGTH_LONG
                        ).show()
                        Test.PlayLoad -> binding.progressBar.visibility = View.VISIBLE
                        Test.StopLoad -> binding.progressBar.visibility = View.GONE
                        is Test.TestImg -> {
                            Log.e(null, "it = ${it.img}")
                            loadImg(it.img)
                        }
                        is Test.ErrorAnswer -> {
                            binding.enterAnswer.error = it.error
                        }
                        is Test.NextStep -> findNavController().navigate(R.id.finishFragment, bundleOf(
                            "countAnswer" to it.count,
                            "countTasks" to countTask
                            ))
                        Test.ClearEditText -> binding.enterAnswer.text.clear()
                    }
                }
            }
        }
    }

    private fun listener() {
        binding.buttonAnswer.setOnClickListener {
            val str = binding.enterAnswer.text.toString().filterNot { it.isWhitespace() }.lowercase()
            viewModel.check(str)
        }
    }

    private fun loadImg(img: String?) {
        Glide
            .with(this)
            .load(img)
            .error(R.drawable.baseline_cloud_off_24)
            .into(binding.imageMain)
    }

}