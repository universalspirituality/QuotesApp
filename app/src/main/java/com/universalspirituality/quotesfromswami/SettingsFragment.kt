package com.universalspirituality.quotesfromswami

import android.appwidget.AppWidgetManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.universalspirituality.quotesfromswami.QuoteWidgetProvider.Companion.updateWidget

class SettingsFragment : Fragment() {

    companion object {
        private const val PREFS_NAME = "com.universalspirituality.quotesfromswami.preferences"
        private const val PREF_PREFIX_KEY = "appwidget_"
        private const val PREF_FREQUENCY_KEY = "_frequency"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_settings, container, false)

        val radioGroupFrequency = view.findViewById<RadioGroup>(R.id.radio_group_frequency)
        val textViewSelectedOption = view.findViewById<TextView>(R.id.text_view_selected_option)
        val getQuotesButton = view.findViewById<Button>(R.id.button_save)

        // Set the default selection
        val radioButton30Minutes = view.findViewById<RadioButton>(R.id.radio_button_30_minutes)
        radioButton30Minutes.isChecked = true

        radioGroupFrequency.setOnCheckedChangeListener { _, checkedId ->
            val selectedOption = when (checkedId) {
                R.id.radio_button_30_minutes -> "30 minutes"
                R.id.radio_button_1_hour -> "1 hour"
                R.id.radio_button_3_hours -> "3 hours"
                R.id.radio_button_6_hours -> "6 hours"
                R.id.radio_button_1_day -> "1 day"
                else -> ""
            }

            val message = getString(R.string.selected_option_message, selectedOption)
            textViewSelectedOption.text = message
        }

        getQuotesButton.setOnClickListener {
            val appWidgetId = arguments?.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )

            val prefs = activity?.getSharedPreferences(PREFS_NAME, 0)
            val editor = prefs?.edit()
            editor?.putLong(
                PREF_PREFIX_KEY + appWidgetId + PREF_FREQUENCY_KEY,
                when (radioGroupFrequency.checkedRadioButtonId) {
                    R.id.radio_button_30_minutes -> 30 * 60 * 1000L
                    R.id.radio_button_1_hour -> 60 * 60 * 1000L
                    R.id.radio_button_3_hours -> 3 * 60 * 60 * 1000L
                    R.id.radio_button_6_hours -> 6 * 60 * 60 * 1000L
                    R.id.radio_button_1_day -> 24 * 60 * 60 * 1000L
                    else -> 30 * 60 * 1000L // default to 30 minutes if no option selected
                }
            )
            editor?.apply()
// Update the widget immediately
            context?.let { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                updateWidget(it)
            }
            }
            Toast.makeText(
                context,
                "Good! The widget will update according to your selection",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Remove the back button from the top bar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return view
    }

}
