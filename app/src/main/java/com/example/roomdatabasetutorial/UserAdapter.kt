package com.example.roomdatabasetutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(var iClickListener: ((user: User) -> Unit)? = null, var iDeleteListener: ((user: User) -> Unit)? = null) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var mListUser: List<User>

    public fun setData(list : List<User>) {
        this.mListUser = list
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        var btnUpdate: Button = itemView.findViewById(R.id.btn_update)
        var btnDelete: Button = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user : User = mListUser[position]
        holder.tvUsername.text = user.username
        holder.tvAddress.text = user.address
        holder.btnUpdate.setOnClickListener{
            iClickListener?.invoke(user)
        }
        holder.btnDelete.setOnClickListener{
            iDeleteListener?.invoke(user)
        }
    }

    override fun getItemCount(): Int {
        return mListUser.size
    }
}