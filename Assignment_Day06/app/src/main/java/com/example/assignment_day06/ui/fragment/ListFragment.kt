package com.example.assignment_day06.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment_day06.database.Database
import com.example.assignment_day06.ui.activity.MainActivity
import com.example.assignment_day06.R
import com.example.assignment_day06.databinding.FragmentListBinding
import com.example.assignment_day06.adapter.TodoAdapter
import com.example.assignment_day06.adapter.TodoAdapter.*

@SuppressLint("ResourceType")
class ListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag", "ListFragment onCreate")
    }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()
    private val adapter = TodoAdapter(Database.todolist)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.d("tag", "ListFragment onCreateView")
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("tag", "ListFragment onViewCreated")
        with(binding) {
            rvList.adapter = adapter
            rvList.layoutManager = LinearLayoutManager(context)
            btnAddFragment.setOnClickListener {
                bundle.clear()
                (activity as MainActivity).apply {
                    openFragment(DetailFragment(), false, bundle)
                }
            }
        }
        itemClick()
    }

    private fun itemClick() {
        adapter.setButtonClickListener(object : OnItemClickListener {
            override fun onClick(position: Int) {
                val note = Database.todolist[position]
                bundle.putSerializable("item", note)
                (activity as MainActivity).apply {
                    openFragment(DetailFragment(), false, bundle)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("tag", "ListFragment onDestroy")
        _binding = null
        val a by lazy {

        }
    }
}
