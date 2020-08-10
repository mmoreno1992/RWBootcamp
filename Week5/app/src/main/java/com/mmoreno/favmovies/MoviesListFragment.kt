package com.mmoreno.favmovies

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmoreno.favmovies.model.Movie
import com.mmoreno.favmovies.ui.LoginActivity
import com.mmoreno.favmovies.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * First and main fragment for displaying the list of movies
 */
class MoviesListFragment : Fragment(), MoviesAdapter.OnMovieClickListener {
    private val adapter: MoviesAdapter by lazy { MoviesAdapter(mutableListOf(), this) }
    private val moviesViewModel: MoviesViewModel by viewModels()

    /**
     * Overriding default method
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Overriding default onCreateView
     * @param inflater object to inflate an XML
     * @param container ViewGroup to the new view be attached later
     * @param savedInstanceState bundle to save/recover state
     * @return [Unit]
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    /**
     * Overriding default onViewCreated method
     * @param view the fragment's view
     * @param savedInstanceState bundle with saved state
     * @return [Unit]
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        moviesViewModel.getAllMovies().observe(viewLifecycleOwner, Observer {
            adapter.updateList(it ?: mutableListOf())
        })

    }

    /**
     * Overriding default method for adding a menu to the fragment
     * @param menu object that represents the menu
     * @param inflater object to inflate the menu resource
     * @return [Unit]
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movies_list_fragment_menu, menu)
    }

    /**
     * Method for responding to items selected in the menu
     * @param item represents the option that has been clicked
     * @return [Boolean] indicating if the options has been managed by
     * this instance
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionLogout) {
            askConfirmationToLogout()
            return true
        } else if (item.itemId == R.id.actionInfo) {
            showCreatedByDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Custom method for showing a view that acts as a it was a dialog
     * and automatically hides after 3 seconds
     */
    private fun showCreatedByDialog() {
        createdByView.visibility = View.VISIBLE
        animateCreatedByView(createdByView.height.toFloat(), 0f)
        Handler().postDelayed({
            view?.let { animateCreatedByView(0f, createdByView.height.toFloat()) }
        }, 3000)
    }

    /**
     * Method for displaying a Dialog and ask the user
     * to confirm the logout of the application
     * @return [Unit]
     */
    private fun askConfirmationToLogout() {
        activity?.let {
            AlertDialog.Builder(it)
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(
                    "No"
                ) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Yes") { _, _ ->
                    doLogout()
                }
                .create()
                .show()
        }
    }

    /**
     * Method for logging out, it updates the IS_LOGGED_IN
     * key in the SharedPreferences file for managing the logged in state
     * @return [Unit]
     */
    private fun doLogout() {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putBoolean(LoginActivity.IS_LOGGED_IN, false)
        editor.apply()
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }

    /**
     * Method to setup the RecyclerView
     * It includes the adapter, animations, and swipe right to delete :D
     * @return [Unit]
     */
    private fun setUpRecyclerView() {

        moviesRecyclerView.layoutManager = LinearLayoutManager(activity)
        moviesRecyclerView.setHasFixedSize(true)
        moviesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        moviesRecyclerView.adapter = adapter
        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                adapter.scrollingDirection = if (dy > 0) MoviesAdapter.ScrollDirection.UP
                else MoviesAdapter.ScrollDirection.DOWN

            }
        })

        //Swipe Right To Delete! :D
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                moviesViewModel.deleteMovie(adapter.moviesList[viewHolder.adapterPosition])
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(moviesRecyclerView)
    }

    /**
     * Method to open the Detail Screen Fragment
     * @param movieId this is the index of the list in my data repository
     * @return [Unit]
     */
    override fun openMovieDetailFragment(holderView: View, movieId: Int) {
        view?.let {
            val imageView = holderView.findViewById<ImageView>(R.id.posterMovie)
            val action =
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(movieId)
            val bundle = Bundle()
            bundle.putInt("movieId", movieId)
            val transitionName = holderView.context.getString(R.string.transitionName)
            val extras = FragmentNavigatorExtras(
                imageView as View to transitionName
            )
            it.findNavController().navigate(action.actionId, bundle, null, extras)
        }
    }

    /**
     * Overriding method for updating a movie in the database
     * @param [movie] instance of [Movie] to be updated
     * @return [Unit]
     */
    override fun updateMovie(movie: Movie) {
        moviesViewModel.updateMovie(movie)
    }

    private fun animateCreatedByView(startValue: Float, endValue: Float) {
        val animator = ObjectAnimator.ofFloat(createdByView, "translationY", startValue, endValue)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 500
        animator.start()
    }

}
