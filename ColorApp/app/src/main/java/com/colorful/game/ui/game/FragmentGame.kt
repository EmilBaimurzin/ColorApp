package com.colorful.game.ui.game

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.colorful.game.R
import com.colorful.game.core.library.l
import com.colorful.game.core.library.random
import com.colorful.game.core.library.soundClickListener
import com.colorful.game.databinding.FragmentGameBinding
import com.colorful.game.domain.game.GameAdapter
import com.colorful.game.ui.other.ViewBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class FragmentGame : ViewBindingFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {
    private lateinit var gameAdapter: GameAdapter
    private val viewModel: GameViewModel by viewModels()
    private val callbackViewModel: CallbackViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initJoystick()

        if (viewModel.gameState) {
            viewModel.startGame()
        }

        callbackViewModel.callback = {
            viewModel.playerColor = it
            viewModel.startGame()
        }

        callbackViewModel.callbackPause = {
            viewModel.startGame()
        }

        binding.buttonPause.soundClickListener {
            viewModel.stopGame()
            viewModel.gameState = false
            findNavController().navigate(R.id.action_fragmentGame_to_dialogPause)
        }

        if (savedInstanceState == null) {
            findNavController().navigate(R.id.action_fragmentGame_to_dialogColor)
        }

        binding.buttonBack.soundClickListener {
            findNavController().popBackStack()
        }

        viewModel.list.observe(viewLifecycleOwner) {
            gameAdapter.items = it.toMutableList()
            gameAdapter.notifyDataSetChanged()
        }

        viewModel.redScores.observe(viewLifecycleOwner) {
            binding.redScoreTextView.text = it.toString()
        }

        viewModel.yellowScores.observe(viewLifecycleOwner) {
            binding.yellowScoreTextView.text = it.toString()
        }

        viewModel.blueScores.observe(viewLifecycleOwner) {
            binding.blueScoreTextView.text = it.toString()
        }

        viewModel.greenScores.observe(viewLifecycleOwner) {
            binding.greenScoreTextView.text = it.toString()
        }

        viewModel.timer.observe(viewLifecycleOwner) {
            binding.timeTextView.text = it.toString()
            if (it == 0) {
                viewModel.endTimer()
                endGame()
            }

            if (it == -1) {
                binding.timeTextView.text = "0"
            }
        }
    }

    private fun endGame() {
        viewModel.stopGame()
        findNavController().navigate(FragmentGameDirections.actionFragmentGameToDialogEnd(viewModel.findMaxScores()))
    }

    private fun initJoystick() {
        binding.joystick.setOnMoveListener { angle, strength ->
            when {
                angle in (46..134) && strength > 40 -> {
                    viewModel.movePlayerUp()
                }

                (angle in (0..45) || angle in (316..359)) && strength > 40 -> {
                    viewModel.movePlayerRight()
                }

                angle in (135..225) && strength > 40 -> {
                    viewModel.movePlayerLeft()
                }

                angle in (226..315) && strength > 40 -> {
                    viewModel.movePlayerDown()
                }

                else -> viewModel.stopPlayer()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("K", 0)
    }

    private fun initAdapter() {
        gameAdapter = GameAdapter()
        with(binding.gameRV) {
            adapter = gameAdapter
            layoutManager = GridLayoutManager(requireContext(), 9).apply {
                orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopGame()
    }
}