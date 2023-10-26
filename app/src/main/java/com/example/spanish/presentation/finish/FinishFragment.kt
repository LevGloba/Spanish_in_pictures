package com.example.spanish.presentation.finish

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.spanish.di.SaveResult
import com.example.spanish.di.model.ChangeStringToolBar
import com.example.spanish.domain.Result
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinishFragment: Fragment(R.layout.fragment_finish) {

    private lateinit var binding: FragmentFinishBinding
    private val viewModel: FinishViewModel by viewModels()
    @Inject
    lateinit var changeStringToolBar: ChangeStringToolBar

    @Inject
    lateinit var saveResult: SaveResult

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
            //changeStringToolBar.change(requireActivity(), getString(R.string.text_logo))
            val result = saveResult.getResult()
            if (result != null)
                sendEmail(result)
            findNavController().navigate(R.id.mainMenuFragment, null, navOptions {
                popUpTo(R.id.mainMenuFragment) {
                    inclusive = true
                }
            })
        }
    }

    private fun sendEmail(result: Result) {
        val subject = "${result.mode}: ${result.theme}: ${result.countAnswer}/${result.countTask}"
        val body = result.answers.toString()
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        intent.type = "message/rfc822"
        requireActivity().startActivity(Intent.createChooser(intent, "Ответы"))
    }

}