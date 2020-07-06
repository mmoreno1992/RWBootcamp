package com.mmoreno.favmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.like.LikeButton
import com.like.OnLikeListener
import com.mmoreno.favmovies.model.Movie
import com.mmoreno.favmovies.viewmodel.MoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * Second fragment that shows the detail of a moview
 */
class MovieDetailFragment : Fragment() {
    private lateinit var movie: Movie
    private lateinit var moviesViewModel: MoviesViewModel


    /**
     * Overriding default method
     * @param savedInstanceState bundle object to store state
     * of the fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
    }

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
                    moviesViewModel.updateMovie(movie)
                }

                override fun unLiked(likeButton: LikeButton?) {
                    movie.isFavorite = false
                    moviesViewModel.updateMovie(movie)
                }
            }
        )
        ratingBarDetail.setOnRatingBarChangeListener { _, rating, _ ->
            run {
                movie.rating = rating
                moviesViewModel.updateMovie(movie)
            }
        }
    }

    /**
     * Method to extract the arguments passed to this detail fragment
     * @return [Unit]
     */
    private fun extractArguments() {
        arguments?.let { it ->
            val args = MovieDetailFragmentArgs.fromBundle(it)
            moviesViewModel.findById(args.movieId).observe(viewLifecycleOwner, Observer {
                movie = it
                showMovie(movie)
            })
        }
    }

    /**
     * Method to extract and show the information
     * about the movie object
     * @param movie instance of Movie to be shown
     * @return [Unit]
     */
    private fun showMovie(movie: Movie) {
        movieTitle.text = movie.title
        releaseDateMovie.text = MoviesAdapter.getFormattedDate(movie.releaseDate)
        genreMovie.text = movie.genre
        summaryMovie.text = movie.summary
        Picasso.get().load(movie.poster).into(posterMovie)
        ratingBarDetail.rating = movie.rating
        favoriteButton.isLiked = movie.isFavorite
    }


}
