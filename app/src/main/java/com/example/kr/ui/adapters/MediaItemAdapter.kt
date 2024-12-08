package com.example.kr.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kr.R
import com.example.kr.model.MediaItem
import com.google.android.material.card.MaterialCardView

class MediaItemAdapter(
    var items: List<MediaItem>,
    private val onItemClick: (MediaItem) -> Unit
) : RecyclerView.Adapter<MediaItemAdapter.MediaItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<MediaItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class MediaItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: MaterialCardView = view.findViewById(R.id.cardView)
        val titleTextView: TextView = view.findViewById(R.id.text_title)
        val descriptionTextView: TextView = view.findViewById(R.id.text_description)
        val genreTextView: TextView = view.findViewById(R.id.text_genre)
        val releaseYearTextView: TextView = view.findViewById(R.id.text_release_year)
        val ratingTextView: TextView = view.findViewById(R.id.text_rating)
        val imageView: ImageView = view.findViewById(R.id.image_view)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(items[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_item_layout, parent, false)
        return MediaItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaItemViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
        holder.descriptionTextView.text = item.description
        holder.genreTextView.text = item.genre
        holder.releaseYearTextView.text = item.releaseYear.toString()
        holder.ratingTextView.text = "Rating: ${item.rating}"
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.imageView)
    }

    override fun getItemCount() = items.size
}
