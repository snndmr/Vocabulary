package com.snn.voc

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_quiz.*


class QuizActivity : AppCompatActivity(), IClickListener {
    private val databaseHelper = DatabaseHelper(this)
    private var words = ArrayList<Word>()
    private var indexOfCorrectAnswer = -1
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        initDatabase()
        createQuestion()
    }

    private fun initDatabase() {
        val names = this.resources.getStringArray(R.array.words)
        val categories = this.resources.getStringArray(R.array.categories)
        val means = this.resources.getStringArray(R.array.means)
        val synonyms = this.resources.getStringArray(R.array.synonyms)
        val antonyms = this.resources.getStringArray(R.array.antonyms)
        val sentences = this.resources.getStringArray(R.array.sentences)

        for (i in names.indices) {
            databaseHelper.insertData(
                Word(
                    0,
                    names[i],
                    categories[i],
                    means[i],
                    synonyms[i],
                    antonyms[i],
                    sentences[i],
                    0
                )
            )
        }

        words = databaseHelper.readData() as ArrayList<Word>
    }

    private fun createQuestion() {
        recyclerViewAnswers.layoutManager = GridLayoutManager(this, 2)

        val mutableSetWords = mutableSetOf<Word>()
        while (mutableSetWords.size < 4) {
            mutableSetWords.add(words.random())
        }

        val answers = arrayListOf<Word>()
        answers.addAll(mutableSetWords)

        val adapter = AnswerAdapter(answers, this)
        recyclerViewAnswers.adapter = adapter

        val correctAnswer = answers.random()

        indexOfCorrectAnswer = answers.indexOf(correctAnswer)
        text_view_question.text = answers[indexOfCorrectAnswer].mean
    }

    override fun listener(position: Int) {
        if (position == indexOfCorrectAnswer) {
            level += 1

            if (level > 10) {
                showMessage(level)
            }

            createQuestion()
            progressBar.progress = level * 10
            text_view_order.text = level.toString()
        } else {
            showMessage(level)
        }
    }

    private fun showMessage(level: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Notice")
        builder.setMessage("Your grade is " + (level * 10))
        builder.setPositiveButton("Ok") { _, _ ->
            finish()
        }
        builder.show()
    }
}