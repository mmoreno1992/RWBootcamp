package com.mmoreno.favmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import com.mmoreno.favmovies.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MoviesAdapter(var moviesList: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    private fun setFavoriteMovie(position: Int, isFavorite: Boolean) {
        moviesList[position].isFavorite = isFavorite
    }

    private fun setRatingMovie(position: Int, rating: Float) {
        moviesList[position].rating = rating
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            setListeners()
        }

        fun bind(movie: Movie) {
            itemView.movieTitle.text = movie.title
            Picasso.get().load(movie.poster).into(itemView.posterMovie)
            itemView.favoriteButton.isLiked = movie.isFavorite
            itemView.ratingBar.rating = movie.rating
        }

        private fun setListeners() {
            //Rating bar listener
            itemView.ratingBar.setOnRatingBarChangeListener { _, rating, _ -> setRatingMovie(adapterPosition, rating) }

            itemView.favoriteButton.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    setFavoriteMovie(adapterPosition, true)
                }

                override fun unLiked(likeButton: LikeButton?) {
                    setFavoriteMovie(adapterPosition, false)
                }

            })
        }
    }

}