package com.universalspirituality.quotesfromswami

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteAdapter(
    private var quotes: List<Quote>,
    private val onQuoteClickListener: OnQuoteClickListener
) :
    RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {
    interface OnQuoteClickListener {
        fun onQuoteClick(quote: Quote)
    }

    class Quote(val text: String)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_quote_grid, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = quotes[position]
        holder.bind(quote)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(quote: Quote) {
            val quoteText = itemView.findViewById<TextView>(R.id.textview_quote_text)
            quoteText.text = quote.text
            itemView.setOnClickListener {
                onQuoteClickListener.onQuoteClick(quote)
            }
        }
    }
}
