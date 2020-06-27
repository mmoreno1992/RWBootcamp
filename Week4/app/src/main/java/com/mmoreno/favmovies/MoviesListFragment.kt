package com.mmoreno.favmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmoreno.favmovies.model.DataRepository
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * First and main fragment for displaying the list of movies
 */
class MoviesListFragment : Fragment(), MoviesAdapter.OnMovieClickListener {
    private lateinit var adapter: MoviesAdapter

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
        adapter = MoviesAdapter(DataRepository.moviesList, this)
        moviesRecyclerView.adapter = adapter
        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                adapter.scrollingDirection = if (dy > 0) MoviesAdapter.ScrollDirection.UP
                else MoviesAdapter.ScrollDirection.DOWN

            }
        })

        //Swipe Right To Delete! :D
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeItemAt(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(moviesRecyclerView)
    }

    /**
     * Method to open the Detail Screen Fragment
     * @param position this is the index of the list in my data repository
     * @return [Unit]
     */
    override fun openMovieDetailFragment(position: Int) {
        view?.let {
            val action =
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(position)
            it.findNavController().navigate(action)
        }
    }

}
