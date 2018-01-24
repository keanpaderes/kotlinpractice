package com.kean.android.kotlinpractice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*

import kotlinx.android.synthetic.main.activity_second.textview_label
import kotlinx.android.synthetic.main.activity_second.textview_random

class SecondActivity : AppCompatActivity() {
    companion object {
        const val TOTAL_COUNT_TAG = "total_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        showRandomNumber()
    }

    fun showRandomNumber() {
        val count = intent.getIntExtra(TOTAL_COUNT_TAG, 0)
        val random = Random()
        var randomInt = 0

        if (count > 0) {
            randomInt = random.nextInt(count+1)
        }

        textview_random.text = Integer.toString(randomInt)

        textview_label.text = getString(R.string.secondactivity_label, count)
    }
}
