package com.example.guess_the_number_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guess_the_number_.databinding.LevelsFragmentLayoutBinding

class LevelsFragment : Fragment() {

    private var _binding: LevelsFragmentLayoutBinding? = null

    private val binding: LevelsFragmentLayoutBinding
        get() = _binding ?: throw RuntimeException("LevelsFragmentLayoutBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LevelsFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

       const val NAME = "LevelsFragment"
        fun newInstance(): LevelsFragment {
            return LevelsFragment()
        }

    }

}