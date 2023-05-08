package gst.trainingcourse.assignment_day04

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(
    var users : List<User>
):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener {
        fun onClick(position: Int)
    }
    fun setButtonClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    inner class UserViewHolder(itemView : View, listener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        init {
            itemView.btnViewDetail.setOnClickListener{
                listener.onClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return UserViewHolder(view,mListener)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.apply {
            tvUserName.text = users[position].username
            tvFirstName.text = users[position].firstname
            tvLastName.text = users[position].lastname
            tvAge.text = users[position].age.toString()
        }
    }
    override fun getItemCount(): Int {
        return users.size
    }
}