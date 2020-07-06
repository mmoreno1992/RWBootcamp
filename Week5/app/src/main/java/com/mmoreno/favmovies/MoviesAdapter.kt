package com.mmoreno.favmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import com.mmoreno.favmovies.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_list.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Adapter for displaying the list of movies
 */
class MoviesAdapter(
    var moviesList: MutableList<Movie>,
    val movieItemListener: OnMovieClickListener
) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var scrollingDirection = ScrollDirection.DOWN

    companion object {
        private const val DATE_FORMAT_2 = "dd-MMM-yyyy"

        /**
         * Helper method for formatting a date
         * @param date Instance of Date to be formatted
         * @return [String] formatted String date
         */
        fun getFormattedDate(date: Date): String? {
            val dateFormat = SimpleDateFormat(DATE_FORMAT_2)
            return dateFormat.format(date)
        }
    }

    /**
     * Overriding method to create ViewHolder's layout
     * @param parent ViewGroup to be attached the new view
     * @param viewType type of the view to be shown
     * @return [ViewHolder] instance of MoviesAdapter.ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return ViewHolder(view)
    }

    /**
     * Method to know how many items the data source has
     * @return [Int] size of the data source
     */
    override fun getItemCount(): Int = moviesList.size

    /**
     * Overriding method for displaying the data inside ViewHolder
     * @param holder instance provided for acting as ViewHolder
     * @param position index of the list
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    /**
     * Method for responding the a click on the heart
     * Change the state of the object setting it as a favorite
     * @return [Unit]
     */
    private fun setFavoriteMovie(position: Int, isFavorite: Boolean) {
        moviesList[position].isFavorite = isFavorite
        movieItemListener.updateMovie(moviesList[position])
    }

    /**
     * Method for changing the rate of the movie
     * @param  position position of the object in the list
     * to change the rating
     * @param rating the new rating to be set to the object
     * @return [Unit]
     */
    private fun setRatingMovie(position: Int, rating: Float) {
        val movie = moviesList[position];
        movie.rating = rating
        movieItemListener.updateMovie(movie)
    }

    /**
     * Method for updating/comparing lists to the RecyclerView
     */
    fun updateList(movies: MutableList<Movie>) {
        val diffCallback = MovieDiffCallback(this.moviesList, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.moviesList.clear()
        this.moviesList.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Inner class defined to act as the ViewHolder for the RecyclerView
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            setListeners()
        }

        /**
         * Helper method to update the information of every object
         * @param movie instance of Movie to be shown
         * @return [Unit]
         */
        fun bind(movie: Movie) {
            itemView.movieTitle.text = movie.title
            Picasso.get().load(movie.poster).into(itemView.posterMovie)
            itemView.favoriteButton.isLiked = movie.isFavorite
            itemView.ratingBar.rating = movie.rating
            itemView.movieGenre.text = movie.genre
            animateViewHolder(itemView)
        }

        /**
         * Helper method to set the view's listeners
         * @return [Unit]
         */
        private fun setListeners() {
            itemView.setOnClickListener(this)
            itemView.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                setRatingMovie(
                    adapterPosition,
                    rating
                )
            }

            itemView.favoriteButton.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    setFavoriteMovie(adapterPosition, true)
                }

                override fun unLiked(likeButton: LikeButton?) {
                    setFavoriteMovie(adapterPosition, false)
                }

            })
        }


        /**
         * Helper method to animate every item in the RecyclerView
         * @param view instance of the RecyclerView to be animated
         * @return [Unit]
         */
        fun animateViewHolder(view: View) {
            if (view.animation == null) {
                val animId =
                    if (scrollingDirection == ScrollDirection.DOWN) R.anim.bottom_slide else R.anim.top_slide
                val animation = AnimationUtils.loadAnimation(view.context, animId)
                view.animation = animation
            }
        }

        /**
         * onClick method to respond to a click and open the DetailFragment
         * @param v object that responds to the event
         * @return [Unit]
         */
        override fun onClick(v: View?) {
            movieItemListener.openMovieDetailFragment(moviesList[adapterPosition].id)
        }
    }

    /**
     * Interface defined to communicate outisde of the Adapter
     */
    interface OnMovieClickListener {
        fun openMovieDetailFragment(movieId: Int)
        fun updateMovie(movie: Movie)
    }

    /**
     * Helper class to define/retrieve the Scroll Direction in the RecyclerView
     * and animate every view based on it.
     */
    enum class ScrollDirection {
        UP, DOWN
    }
}