package com.attestation_finale_work.presentation.screens.discrete

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class DiscreteCommonFragment : Fragment() {
    
    fun showToast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}