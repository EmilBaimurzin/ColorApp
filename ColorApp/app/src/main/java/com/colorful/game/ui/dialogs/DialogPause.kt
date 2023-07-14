package com.colorful.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.colorful.game.R
import com.colorful.game.core.library.ViewBindingDialog
import com.colorful.game.core.library.soundClickListener
import com.colorful.game.databinding.DialogPauseBinding
import com.colorful.game.ui.game.CallbackViewModel

class DialogPause: ViewBindingDialog<DialogPauseBinding>(DialogPauseBinding::inflate) {
    private val viewModel: CallbackViewModel by activityViewModels()
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
            buttonContinue.soundClickListener {
                viewModel.callbackPause?.invoke()
                findNavController().popBackStack()
            }
        }
    }
}