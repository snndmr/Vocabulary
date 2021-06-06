package com.snn.voc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TypeAdapter (private val types: ArrayList<String>, clickListener: IClickListener) :
    RecyclerView.Adapter<TypeAdapter.Holder>() {

    private var clickListener: IClickListener? = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.answer_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItems(types[position], position, clickListener)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(
            type: String,
            position: Int,
            clickListener: IClickListener?
        ) {
            val cardText = itemView.findViewById(R.id.text_view_answer) as TextView
            cardText.text = type

            itemView.setOnClickListener {
                clickListener?.listener("type", position)
            }
        }
    }
}