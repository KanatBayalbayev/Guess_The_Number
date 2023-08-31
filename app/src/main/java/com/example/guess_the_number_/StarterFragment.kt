package com.example.guess_the_number_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guess_the_number_.databinding.WelcomeFragmentLayoutBinding

class StarterFragment : Fragment() {

    private var _binding: WelcomeFragmentLayoutBinding? = null

    private val binding: WelcomeFragmentLayoutBinding
        get() = _binding ?: throw RuntimeException("WelcomeFragmentLayoutBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WelcomeFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonToStartGame.setOnClickListener {
            launchLevelFragment()
        }
    }

    private fun launchLevelFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LevelsFragment.newInstance())
            .addToBackStack(LevelsFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}