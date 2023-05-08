package com.example.assignment_day06.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_day06.databinding.ItemNoteBinding
import com.example.assignment_day06.model.Todo

class TodoAdapter(
    var todos : List<Todo>
):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private lateinit var mListener: OnItemClickListener
    inner class TodoViewHolder(val binding: ItemNoteBinding):RecyclerView.ViewHolder(binding.root)
    interface OnItemClickListener {
        fun onClick(position: Int)
    }
    fun setButtonClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.binding.noteItem.setOnClickListener{
            mListener.onClick(position)
        }
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        with(holder.binding) {
            tvTitle.text = todos[position].title
            tvText.text = todos[position].text
            cbDone.isChecked = todos[position].isChecked
            cbDoneClick(position,holder.binding)
            cbDone.setOnClickListener{
                todos[position].isChecked = cbDone.isChecked
                cbDoneClick(position,holder.binding)
            }
        }
    }
    private fun cbDoneClick(position: Int, binding: ItemNoteBinding){
        with(binding){
            tvTitle.apply {
                (if (todos[position].isChecked) Paint.STRIKE_THRU_TEXT_FLAG else Paint.ANTI_ALIAS_FLAG).also { this.paintFlags = it }
            }
            tvText.apply {
                (if (todos[position].isChecked) Paint.STRIKE_THRU_TEXT_FLAG else Paint.ANTI_ALIAS_FLAG).also { this.paintFlags = it }
            }
        }
    }
    override fun getItemCount(): Int {
        return todos.size
    }
}

