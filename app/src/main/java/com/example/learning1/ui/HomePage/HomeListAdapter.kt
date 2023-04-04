package com.example.learning1.ui.HomePage

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.learning1.R
import com.example.learning1.data.Note
import io.getstream.avatarview.AvatarView

class HomeListAdapter(val context: Context,
                      var list: ArrayList<Note>,
                      val listner: OnClickDeleteListener,
                      val editListner: OnClickEditListner):
    RecyclerView.Adapter<HomeListAdapter.MyViewHolder>(){


    public fun setList(list: List<Note>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_notes, parent, false);
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(!list[position].color.trim().isEmpty()) {
            holder.card.setBackgroundColor(Color.parseColor(list[position].color))
        }
        holder.Title.text = list[position].note_name
        holder.Description.text = list[position].note_description
        Log.e("INITIALS","" + list[position].note_name[0].toString())
        holder.avatar.avatarInitials = list[position].note_name[0].toString()
//        holder.avatar.avatarInitialsTextColor = Color.BLACK
//        holder.avatar.avatarInitialsBackgroundColor = Color.BLACK
//        holder.avatar.avatarInitialsTextSize = 200
        //holder.avatar.avatarInitials = list[position].note_name[0].toString()
        if(list[position].date != null) {
            holder.date.text =list[position].date
        }
        if(list[position].completed == false) {
            holder.deleteButton.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24)
        }

        holder.deleteButton.setOnClickListener {
            if(listner != null) {
                listner.onClick(list[position], holder.deleteButton)
            }

        }
        holder.itemView.setOnClickListener {
            if(editListner != null) {
                editListner.editListner(list[position])
            }
        }
    }

    override fun getItemCount() = list.size

    class MyViewHolder(view: View): ViewHolder(view.rootView) {
         val Title = view.findViewById<TextView>(R.id.title)
         val Description = view.findViewById<TextView>(R.id.description)
         val deleteButton = view.findViewById<ImageButton>(R.id.more)
         val avatar = view.findViewById<AvatarView>(R.id.avatar)
        val date  = view.findViewById<TextView>(R.id.date)
        val card = view.findViewById<CardView>(R.id.cardId)

    }

    class OnClickDeleteListener(val listener: (note: Note, view: View) -> Unit) {
        fun onClick(note: Note, view: View) = listener(note, view)
    }

    class OnClickEditListner(val editListner: (note:Note) -> Unit) {
        fun onClick(note: Note) = editListner(note)
    }
}


