package com.mmoreno.favmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.like.LikeButton
import com.like.OnLikeListener
import com.mmoreno.favmovies.model.DataRepository
import com.mmoreno.favmovies.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * Second fragment that shows the detail of a moview
 */
class MovieDetailFragment : Fragment() {
    lateinit var movie: Movie

    /**
     * Overriding default onCreateView
     * @param inflater object to inflate an XML
     * @param container viewgroup to the new view be attached later
     * @param savedInstanceState bundle to save/recover state
     * @return [Unit]
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    /**
     * Overriding default onViewCreated method
     * @param view the fragment's view
     * @param savedInstanceState bundle with saved state
     * @return [Unit]
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractArguments()
        setListeners()
    }

    /**
     * Custom method for setting the listeners
     * @return [Unit]
     */
    private fun setListeners() {
        favoriteButton.setOnLikeListener(
            object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    movie.isFavorite = true
                }

                override fun unLiked(likeButton: LikeButton?) {
                    movie.isFavorite = false
                }
            }
        )
        ratingBarDetail.setOnRatingBarChangeListener {
                _, rating, _ ->
            run {
                movie.rating = rating
            }
        }
    }

    /**
     * Method to extract the arguments passed to this detail fragment
     * @return [Unit]
     */
    private fun extractArguments() {
        arguments?.let {
            val args = MovieDetailFragmentArgs.fromBundle(it)
            movie = DataRepository.moviesList[args.movieListPosition]
            showMovie(movie)
        }
    }

    /**
     * Method to extract and show the information
     * about the movie object
     * @param movie instance of Movie to be shown
     * @return [Unit]
     */
    private fun showMovie(movie: Movie) {
        movieTitle.text = view?.context?.getString(movie.title)
        releaseDateMovie.text = MoviesAdapter.getFormattedDate(movie.releaseDate)
        genreMovie.text = view?.context?.getString(movie.genre)
        summaryMovie.text = getString(movie.summary)
        Picasso.get().load(movie.poster).into(posterMovie)
        ratingBarDetail.rating = movie.rating
        favoriteButton.isLiked = movie.isFavorite
    }

}
