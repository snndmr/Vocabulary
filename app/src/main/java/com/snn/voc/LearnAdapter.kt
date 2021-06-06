package com.snn.voc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LearnAdapter(private val words: ArrayList<Word>, clickListener: IClickListener) :
    RecyclerView.Adapter<LearnAdapter.Holder>() {
    var clickListener: IClickListener? = clickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItems(words[position], position, clickListener)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(
            word: Word,
            position: Int,
            clickListener: IClickListener?
        ) {
            val name = itemView.findViewById(R.id.text_view_word) as TextView
            val type = itemView.findViewById(R.id.text_view_type) as TextView
            val mean = itemView.findViewById(R.id.text_view_mean) as TextView
            val synonym = itemView.findViewById(R.id.text_view_synonym) as TextView
            val antonym = itemView.findViewById(R.id.text_view_antonym) as TextView
            val sentence = itemView.findViewById(R.id.text_view_sentence) as TextView

            name.text = word.name
            type.text = word.type
            mean.text = word.mean
            synonym.text = word.synonym
            antonym.text = word.antonym
            sentence.text = word.sentence

            clickListener?.listener("card", position)
        }
    }
}