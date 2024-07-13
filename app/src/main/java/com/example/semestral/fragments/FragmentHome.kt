package com.example.semestral.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment

class FragmentHome : Fragment() {

    private lateinit var viewFlipper: ViewFlipper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewFlipper = view.findViewById(R.id.viewFlipper)
        viewFlipper.inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.in_from_right)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.out_to_left)

        // Return the inflated view
        return view
    }
}
