package com.universalspirituality.quotesfromswami

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(), QuoteAdapter.OnQuoteClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("HomeFragment", "onCreateView() called")

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.quoteRecyclerViewHome)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        val databaseHelper = DatabaseHelper(requireContext())
        val quotes = databaseHelper.getAllQuotes().map { QuoteAdapter.Quote(it) }
        val adapter = QuoteAdapter(quotes, this) // passing the listener
        recyclerView.adapter = adapter

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)


        return view
    }

    override fun onQuoteClick(quote: QuoteAdapter.Quote) {
        val intent = Intent(requireContext(), FullscreenQuoteActivity::class.java)
        intent.putExtra(FullscreenQuoteActivity.EXTRA_QUOTE_TEXT, quote.text)
        startActivity(intent)
    }

}
