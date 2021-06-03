package com.snn.voc

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        startService(Intent(this, NotificationService::class.java))
        initDatabase()

        buttonLearn.setOnClickListener {
            val intent = Intent(this, LearnActivity::class.java)
            startActivity(intent)
        }

        buttonQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        floating_add.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.word_add_dialog)
            dialog.setCancelable(true)

            val word = dialog.findViewById(R.id.edit_text_word) as EditText
            val type = dialog.findViewById(R.id.edit_text_type) as EditText
            val mean = dialog.findViewById(R.id.edit_text_mean) as EditText
            val synonym = dialog.findViewById(R.id.edit_text_synonym) as EditText
            val antonym = dialog.findViewById(R.id.edit_text_antonym) as EditText
            val sentence = dialog.findViewById(R.id.edit_text_sentence) as EditText

            val cancel = dialog.findViewById(R.id.cancel_button) as Button
            cancel.setOnClickListener {
                dialog.dismiss()
            }

            val add = dialog.findViewById(R.id.add_button) as Button
            add.setOnClickListener {
                if (word.text.toString().isEmpty() || type.text.toString().isEmpty() ||
                    mean.text.toString().isEmpty() || sentence.text.toString().isEmpty()
                ) {
                    AlertDialog.Builder(this).setTitle("Notice")
                        .setMessage("You must fill in the word, type, mean and sentence blanks.")
                        .setPositiveButton("Ok") { _, _ -> }.show()
                } else {
                    databaseHelper.insertData(
                        Word(
                            0,
                            word.text.toString(),
                            type.text.toString(),
                            mean.text.toString(),
                            synonym.text.toString(),
                            antonym.text.toString(),
                            sentence.text.toString(),
                            1
                        )
                    )
                    dialog.dismiss()
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        word.text.toString() + " added",
                        Snackbar.LENGTH_LONG
                    ).setAnchorView(floating_add).show()
                }
            }
            dialog.show()
        }
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
    }
}