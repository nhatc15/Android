package com.example.assignment_day06.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.assignment_day06.R
import com.example.assignment_day06.database.Database
import com.example.assignment_day06.viewModel.ViewModel
import com.example.assignment_day06.databinding.FragmentDetailBinding
import com.example.assignment_day06.model.Todo
@SuppressLint("ResourceType")
class DetailFragment : Fragment(R.id.fragment_detail) {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val model : ViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("tag", "DeatailFragment onCreateView")
        _binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("tag", "DetailFragment onViewCreated")

        display()

        binding.btnAdd.setOnClickListener{
            saveNote()
        }
    }
    fun display(){
        if(requireArguments().containsKey("item")){
            val note = arguments?.getSerializable("item") as Todo
            with(binding){
                addEditText.setText("Edit Note")
                with(btnDelete){
                    visibility = VISIBLE
                    setOnClickListener{
                        model.removeNote(note)
                        parentFragmentManager.popBackStack()
                    }
                }
                etTitle.setText(note.title)
                etText.setText(note.text)
            }
        }
    }
    fun saveNote(){
        val title = binding.etTitle.text.toString()
        val text = binding.etText.text.toString()
        val noteTodo = model.getNote(title)
        if(noteTodo == null){
            model.addNote(Todo(title,text,false))
        }else{
            model.updateNote(title,text,noteTodo)
        }
        parentFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("tag", "DetailFragment onDestroy")
        _binding = null
    }
}