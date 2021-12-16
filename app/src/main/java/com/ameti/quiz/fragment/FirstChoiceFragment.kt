package com.ameti.quiz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.ameti.quiz.R

class FirstChoiceFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_first_choice, container, false)
        rootView = inflate.rootView

        rootView.findViewById<Button>(R.id.login_btn).apply {
            setOnClickListener {
                it.findNavController().navigate(R.id.action_firstChoiceFragment_to_loginFragment)
            }
        }

        rootView.findViewById<Button>(R.id.register_btn).setOnClickListener {
            it.findNavController().navigate(R.id.action_navHostFragment_to_registerFragment)
        }

        return inflate
    }

}