package com.snn.voc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity : AppCompatActivity() {
    private val databaseHelper = DatabaseHelper(this)
    private var words = ArrayList<Word>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        setup()
        randomWord()
        floating_next.setOnClickListener {
            randomWord()
        }
    }

    private fun randomWord() {
        val word: Word = words.random()

        text_view_word.text = word.name
        text_view_type.text = word.type
        text_view_mean.text = word.mean
        text_view_synonym.text = word.synonym
        text_view_antonym.text = word.antonym
        text_view_sentence.text = word.sentence
    }

    private fun setup() {
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
}