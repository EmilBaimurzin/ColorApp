package com.colorful.game.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.colorful.game.R
import com.colorful.game.core.library.soundClickListener
import com.colorful.game.databinding.FragmentMenuBinding
import com.colorful.game.ui.other.ViewBindingFragment

class FragmentMain: ViewBindingFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonStart.soundClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentGame)
        }
        binding.buttonExit.soundClickListener {
            requireActivity().finish()
        }
    }
}