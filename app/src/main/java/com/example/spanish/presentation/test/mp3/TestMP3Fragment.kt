package com.example.spanish.presentation.test.mp3

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.LocaleList
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
import com.example.spanish.R
import com.example.spanish.databinding.FragmentTestMp3Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.properties.Delegates


@AndroidEntryPoint
class TestMP3Fragment: Fragment() {

    private val testMP3ViewModel by viewModels<TestMP3ViewModel>()

    private lateinit var binding: FragmentTestMp3Binding
    private var mPlayer: MediaPlayer? = null
    private var countTask by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPlayer = MediaPlayer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_mp3, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countTask = arguments?.getInt("countTasks",5)?: 5
        testMP3ViewModel.setCountTaskAndModeTesting(
            countTask,
            arguments?.getBoolean("modeTetsting")?: false
        )
        binding = FragmentTestMp3Binding.bind(view)
        binding.enterAnswer.imeHintLocales = LocaleList(Locale("es", "ES"))
        initListenerButton()
        initListenerViewModel()
    }

    override fun onPause() {
        super.onPause()
        mPlayer?.pause()
    }

    override fun onStop() {
        super.onStop()
        mPlayer?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer?.release()
        mPlayer = null
    }

    private fun pause() {
        mPlayer?.pause()
        binding.icPlayPause.setImageResource(R.drawable.baseline_play_circle_24)
    }

    private fun play() {
        mPlayer?.start()
        viewLifecycleOwner.lifecycleScope.launch {
            while (mPlayer?.isPlaying == true) {
                binding.seekBar.max = mPlayer?.duration?: 0
                binding.seekBar.progress = mPlayer?.currentPosition?: 0
                delay(50)
            }
        }
        binding.icPlayPause.setImageResource(R.drawable.baseline_pause_circle_24)
    }

    private fun initListenerButton() {
        binding.run {
            icPlayPause.setOnClickListener {
                if (mPlayer?.isPlaying == true)
                    pause()
                else if (mPlayer?.isPlaying == false) {
                    play()
                }
            }
            binding.buttonAnswer.setOnClickListener {
                val str = binding.enterAnswer.text.toString().filterNot { it.isWhitespace() }.lowercase()
                testMP3ViewModel.check(str)
            }
        }
    }

    private fun initMediaPlayer(id: Int) {
        mPlayer?.reset()
        binding.icPlayPause.setImageResource(R.drawable.baseline_play_circle_24)
        mPlayer?.let {
            mediaPlayer ->
            mediaPlayer.setDataSource(requireContext(), Uri.parse("android.resource://"+ requireContext().packageName + "/" + id))
            mediaPlayer.prepare()
            mediaPlayer.setOnCompletionListener {
                if (!it.isPlaying)
                    pause()
            }
        }
    }

    private fun initListenerViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                testMP3ViewModel.state.collect {
                    when(it) {
                        is TestMP3State.IdSound -> {
                            initMediaPlayer(it.id)
                        }
                        is TestMP3State.ErrorMessange -> {
                            Toast.makeText(requireContext(), it.e,Toast.LENGTH_LONG).show()
                        }
                        is TestMP3State.ErrorAnswer -> {
                            binding.enterAnswer.error = it.e
                        }
                        is TestMP3State.NextStep -> findNavController().navigate(R.id.finishFragment, bundleOf(
                            "countAnswer" to it.count,
                            "countTasks" to countTask
                        )
                        )
                        TestMP3State.ClearEditText -> binding.enterAnswer.text.clear()
                    }
                }
            }
        }
    }

}