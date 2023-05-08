package com.example.gst_lesson7_8_ex1_nhatnv15.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gst_lesson7_8_ex1_nhatnv15.databinding.FragmentPlayingBinding
import com.example.gst_lesson7_8_ex1_nhatnv15.model.Song


/**
* A simple [Fragment] subclass.
* Use the [PlayingFragment.newInstance] factory method to
* create an instance of this fragment.
*/


class PlayingFragment : Fragment() {
    private var _binding: FragmentPlayingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = context as MainActivity
        binding.ivPrev.setOnClickListener {
            activity.prev()
        }
        binding.ivNext.setOnClickListener {
            activity.next()
        }
        binding.ivPlay.setOnClickListener {
            activity.pause()
        }
        binding.ivPause.setOnClickListener{

        }
    }
}