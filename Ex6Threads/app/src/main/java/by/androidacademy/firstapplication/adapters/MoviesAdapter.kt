package by.androidacademy.firstapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.androidacademy.firstapplication.R
import by.androidacademy.firstapplication.data.ListMovies
import by.androidacademy.firstapplication.data.Movie

class MoviesAdapter(
    private val movies: List<Movie>,
    private val clickListener: (position: Int) -> Unit
) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(DiffCallback) {
//    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getMovie(position))
    }

    override fun getItemCount(): Int = movies.size

    private fun getMovie(position: Int): Movie = movies[position]

    class ViewHolder(
        itemView: View,
        listener: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.ivPoster)
        private val title: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val overview: TextView = itemView.findViewById(R.id.tvMovieOverview)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener(position)
                }
            }
        }

        fun bind(movie: Movie) {
            poster.setImageResource(movie.posterRes)
            title.text = movie.title
            overview.text = movie.overview
        }
    }
}