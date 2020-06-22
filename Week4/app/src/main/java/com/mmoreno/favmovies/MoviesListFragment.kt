package com.mmoreno.favmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmoreno.favmovies.model.DataRepository
import kotlinx.android.synthetic.main.fragment_movies_list.*


class MoviesListFragment : Fragment() {
    private lateinit var adapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        /*botonNavegar.setOnClickListener {
           view?.let {
               it.findNavController().navigate(R.id.action_moviesListFragment_to_movieDetailFragment)
           }
        }*/
    }

    private fun setUpRecyclerView() {
        moviesRecyclerView.layoutManager = LinearLayoutManager(activity)
        moviesRecyclerView.setHasFixedSize(true)
        adapter = MoviesAdapter(DataRepository.moviesList)
        moviesRecyclerView.adapter = adapter

    }

}
