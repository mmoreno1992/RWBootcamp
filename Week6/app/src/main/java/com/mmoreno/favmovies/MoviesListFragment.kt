package com.mmoreno.favmovies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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
    private lateinit var adapter: MoviesAdapter
    private lateinit var moviesViewModel: MoviesViewModel

    /**
     * Overriding default method
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        setHasOptionsMenu(true)
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
        moviesViewModel.getAllMovies().observe(this.viewLifecycleOwner, Observer {
            adapter.submitList(it)

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
        }
        return super.onOptionsItemSelected(item)
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
     * Method for logging out, it updates the [LoginActivity.IS_LOGGED_IN]
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
        moviesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        adapter = MoviesAdapter(this)
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
                moviesViewModel.deleteMovie(adapter.getMovie(viewHolder.adapterPosition))
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
    override fun openMovieDetailFragment(movieId: Int) {
        view?.let {
            val action =
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(movieId)
            it.findNavController().navigate(action)
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

}
