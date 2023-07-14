package com.colorful.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.colorful.game.R
import com.colorful.game.core.library.ViewBindingDialog
import com.colorful.game.core.library.soundClickListener
import com.colorful.game.databinding.DialogColorBinding
import com.colorful.game.domain.game.Character
import com.colorful.game.ui.game.CallbackViewModel

class DialogColor: ViewBindingDialog<DialogColorBinding>(DialogColorBinding::inflate) {
    private val callbackViewModel: CallbackViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                findNavController().popBackStack(R.id.fragmentMain, false, false)
                true
            } else {
                false
            }
        }
        dialog?.setCancelable(false)

        binding.apply {
            buttonClose.soundClickListener {
                findNavController().popBackStack(R.id.fragmentMain, false, false)
            }
        }

        binding.redButton.soundClickListener {
            findNavController().popBackStack()
            callbackViewModel.callback?.invoke(Character.RED)
        }
        binding.blueButton.soundClickListener {
            findNavController().popBackStack()
            callbackViewModel.callback?.invoke(Character.BLUE)
        }
        binding.yellowButton.soundClickListener {
            findNavController().popBackStack()
            callbackViewModel.callback?.invoke(Character.YELLOW)
        }
        binding.greenButton.soundClickListener {
            findNavController().popBackStack()
            callbackViewModel.callback?.invoke(Character.GREEN)
        }
    }
}