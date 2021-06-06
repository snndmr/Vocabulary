package com.snn.voc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_quiz.*


class QuizActivity : AppCompatActivity(), IClickListener {
    private val databaseHelper = DatabaseHelper(this)
    private val types = arrayListOf("Verb", "Adverb", "Adjective", "Phrase and Idiom")

    private var words = ArrayList<Word>()
    private var indexOfCorrectAnswer = -1
    private var level = 1
    private var selectedType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        progressBar.alpha = 0f
        text_view_order.alpha = 0f

        recyclerViewAnswers.layoutManager = GridLayoutManager(this, 2)

        createTypeQuestion()
    }

    private fun createQuestion() {
        val mutableSetWords = mutableSetOf<Word>()
        while (mutableSetWords.size < 4) {
            mutableSetWords.add(words.random())
        }

        val answers = ArrayList<Word>(mutableSetWords)
        indexOfCorrectAnswer = answers.indexOf(answers.random())
        text_view_question.text = answers[indexOfCorrectAnswer].mean

        recyclerViewAnswers.adapter = AnswerAdapter(answers, this)
    }

    @SuppressLint("SetTextI18n")
    private fun createTypeQuestion() {
        text_view_question.text = "Specify the type of words"
        recyclerViewAnswers.adapter = TypeAdapter(types, this)
    }

    override fun listener(id: String, position: Int) {
        if (id == "type") {
            selectedType = types[position]
            words = databaseHelper.readTypeData(selectedType) as ArrayList<Word>

            progressBar.animate().alpha(1f).duration = 1000
            text_view_order.animate().alpha(1f).duration = 1000

            createQuestion()
        }

        if (id == "answer") {
            if (position == indexOfCorrectAnswer) {
                level += 1

                if (level >= 10) {
                    showMessage("Congratulations!", "You made 10 out of 10!")
                } else {
                    createQuestion()
                    progressBar.progress = level * 10
                    text_view_order.text = level.toString()
                }
            } else {
                showMessage("You failed!", "You made $level out of 10!")
            }
        }
    }

    private fun showMessage(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { _, _ -> finish() }
            .show()
    }
}