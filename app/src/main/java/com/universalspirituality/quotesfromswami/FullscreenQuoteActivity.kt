package com.universalspirituality.quotesfromswami

import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FullscreenQuoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUOTE_TEXT = "extra_quote_text"
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_fullscreen)

        // Hide the toolbar
        supportActionBar?.hide()


        val quoteText = intent.getStringExtra(EXTRA_QUOTE_TEXT)
        Log.d("FullscreenQuoteActivity", "Quote text received: $quoteText")

        val quoteTextView = findViewById<TextView>(R.id.textView3)
        quoteTextView.text = quoteText

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        fab.setOnClickListener {
            // Hide the FAB button
            fab.visibility = View.GONE
            // Capture the screen as a bitmap
            val bitmap = Utils.captureScreen(findViewById<ConstraintLayout>(R.id.quote_container))

            // Crop the bitmap to the desired area
            val rect = Rect(0, 0, bitmap.width, fab.top)
            val croppedBitmap = Utils.cropBitmap(bitmap, rect)

            // Share the bitmap image
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val uri = Utils.saveImageAndGetUri(this@FullscreenQuoteActivity, croppedBitmap)
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            startActivity(Intent.createChooser(shareIntent, "Share quote via"))
            // Show the FAB button
            fab.visibility = View.VISIBLE
        }
        val homeButton = findViewById<FloatingActionButton>(R.id.homeButton123)
        homeButton.setOnClickListener {

            finish()
        }

        }


    }

