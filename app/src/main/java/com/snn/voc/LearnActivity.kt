package com.snn.voc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_learn.*


class LearnActivity : AppCompatActivity(), IClickListener {
    private val databaseHelper = DatabaseHelper(this)
    private var words = ArrayList<Word>()
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        words = databaseHelper.readData() as ArrayList<Word>

        recyclerViewLearn.layoutManager = CustomLayoutManager(this)
        recyclerViewLearn.adapter = LearnAdapter(words, this)
        SnapHelper().attachToRecyclerView(recyclerViewLearn)

        floating_remind_learn.setOnClickListener {
            databaseHelper.updateUserData(words[position], 1)
            Snackbar.make(
                findViewById(android.R.id.content),
                "Reminder added!",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun listener(id: String, position: Int) {
        this.position = position
    }
}