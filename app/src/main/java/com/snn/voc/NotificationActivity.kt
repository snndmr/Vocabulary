package com.snn.voc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.word_item.*

class NotificationActivity : AppCompatActivity() {
    private val databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        if (databaseHelper.readUserData().size > 0) {
            val word = databaseHelper.readUserData().random()

            text_view_word.text = word.name
            text_view_type.text = word.type
            text_view_mean.text = word.mean
            text_view_synonym.text = word.synonym
            text_view_antonym.text = word.antonym
            text_view_sentence.text = word.sentence

            notification_card.visibility = View.VISIBLE
            linearLayoutButtons.visibility = View.VISIBLE

            floating_remind.setOnClickListener {
                showMessage("Word will show again!")
            }

            floating_memorized.setOnClickListener {
                databaseHelper.updateUserData(word, 0)
                showMessage("Word memorized!")
            }
        } else {
            showMessage("There are no words added by the user.")
        }
    }

    @SuppressLint("ShowToast")
    fun showMessage(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                startActivity(Intent(this@NotificationActivity, MainActivity::class.java))
            }
        }).show()
    }
}