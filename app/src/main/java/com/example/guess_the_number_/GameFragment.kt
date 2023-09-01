package com.example.guess_the_number_

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.guess_the_number_.databinding.GameFragmentLayoutBinding
import kotlin.random.Random

class GameFragment : Fragment() {

    private var _binding: GameFragmentLayoutBinding? = null
    private val binding: GameFragmentLayoutBinding
        get() = _binding ?: throw RuntimeException("GameFragmentLayoutBinding == null")

    private lateinit var level: String

    private var lives = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        level = arguments?.getString(KEY_LEVEL).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val millSeconds = when (level) {
            "easy" -> 61000L
            "medium" -> 31000L
            "hard" -> 11000L
            else -> 71000L
        }
        val maxRandomNumber = when (level) {
            "easy" -> 10
            "medium" -> 20
            "hard" -> 30
            else -> 40
        }
        val randomNumber = Random.nextInt(1, maxRandomNumber)

        when (level) {
            "easy" -> binding.number.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )

            "medium" -> binding.number.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )

            "hard" -> binding.number.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )

            else -> throw RuntimeException("Error")
        }

        binding.number.text = "Guess a Number Between 1-$maxRandomNumber"

        Log.d("GameFragment", "randomNumber: $randomNumber")


        val starTimer = object : CountDownTimer(millSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timer.text = formatTime(millisUntilFinished)

            }

            override fun onFinish() {
                binding.buttonToStartNewGame.alpha = 1F
                binding.buttonToCheck.visibility = View.GONE
                binding.timer.text = "You lost the game"
            }
        }
        starTimer.start()
        binding.buttonToCheck.setOnClickListener {
            val inputFromUser = binding.userInput.text.toString().trim()

            if (inputFromUser == ""){
                binding.resultText.text = resources.getString(R.string.warning)
            }

            if (inputFromUser == randomNumber.toString()) {
                binding.timer.text = "Congratulations \uD83D\uDCA5"
                binding.resultSmile.text = resources.getString(R.string.winnerSmile)
                binding.randomNumber.text = "$randomNumber"
                binding.buttonToCheck.visibility = View.GONE
                binding.buttonToStartNewGame.visibility = View.VISIBLE
                binding.resultText.text = resources.getString(R.string.winner)
                binding.resultText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

                starTimer.cancel()
            } else if (inputFromUser > randomNumber.toString()) {
                binding.resultText.text = resources.getString(R.string.your_guess_is_too_high)
                lives--

            } else if (inputFromUser < randomNumber.toString()){
                binding.resultText.text = resources.getString(R.string.your_guess_is_too_low)
                lives--
            }

            if (lives == 2) {
                binding.firstLife.visibility = View.GONE
            } else if (lives == 1) {
                binding.secondLife.visibility = View.GONE
            } else if (lives == 0){
                binding.thirdLife.visibility = View.GONE
                binding.timer.text = "You lost the game"
                binding.buttonToCheck.visibility = View.GONE
                binding.buttonToStartNewGame.visibility = View.VISIBLE
                binding.resultSmile.text = "\uD83D\uDE15"
                binding.randomNumber.text = "$randomNumber"
                binding.chance.text = "No more chances \uD83E\uDD37\u200D♂️"
                starTimer.cancel()
            }


        }

        binding.buttonToStartNewGame.setOnClickListener {
            launchGame()
        }
    }
    private fun launchGame(){
        requireActivity().supportFragmentManager.popBackStack()
    }
    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / 1000L
        val minutes = seconds / 60
        val leftSeconds = seconds - (minutes * 60)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun logicGame(userInput: String, randomNumber: String) {
        if (userInput === randomNumber) {
            binding.timer.text = "Congratulations \uD83D\uDCA5"
            binding.resultSmile.text = "\uD83E\uDD73"
        } else if (userInput > randomNumber) {
            binding.resultSmile.text = "Your guess is too low \n \uD83D\uDE2C"
            lives--
            binding.firstLife.visibility = View.GONE

        } else if (userInput < randomNumber) {
            binding.resultSmile.text = "Your guess is too high \n \uD83D\uDE44"
            lives--
            binding.firstLife.visibility = View.GONE
        }
        Log.d("GameFragment", "userInput: $userInput")
        Log.d("GameFragment", "randomNumber: $randomNumber")


    }


    companion object {
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"


        fun newInstance(level: String): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_LEVEL, level)
                }
            }
        }

    }


}