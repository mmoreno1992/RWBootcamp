package com.mmoreno.favmovies.model

import com.mmoreno.favmovies.R
import java.time.LocalDate
import java.util.*

/**
 * Data source repository (Singleton)
 */
object DataRepository {
    val moviesList = mutableListOf<Movie>()

    init {
        moviesList.add(
            Movie(
                1,
                Date(System.currentTimeMillis() - 120000000),
                R.string.jokerTitle,
                R.string.jokerDescription,
                R.drawable.joker,
                R.string.genreDrama
            )
        )
        moviesList.add(
            Movie(
                2,
                Date(System.currentTimeMillis() - 3400000000000L),
                R.string.endGameTitle,
                R.string.endGameDescription,
                R.drawable.end_game,
                R.string.genreAction
            )
        )
        moviesList.add(
            Movie(
                3,
                Date(System.currentTimeMillis() - 350000000000L),
                R.string.cocoTitle,
                R.string.cocoDescription,
                R.drawable.coco,
                R.string.genreComedy
            )
        )
        moviesList.add(
            Movie(
                4,
                Date(System.currentTimeMillis() - 360000000000L),
                R.string.badBoysTitle,
                R.string.badBoysDescription,
                R.drawable.bad_boys,
                R.string.genreAction
            )
        )
        moviesList.add(
            Movie(
                5,
                Date(System.currentTimeMillis() - 400000000000L),
                R.string.jumanjiTitle,
                R.string.jumanjiNextLevelDescription,
                R.drawable.jumanji,
                R.string.genreAction
            )
        )
        moviesList.add(
            Movie(
                6,
                Date(System.currentTimeMillis() - 500000000000L),
                R.string.hannibalTitle,
                R.string.hannibalDescription,
                R.drawable.hannibal,
                R.string.genreHorror
            )
        )
        moviesList.add(
            Movie(
                7,
                Date(System.currentTimeMillis() - 600000000000L),
                R.string.sherlockTitle,
                R.string.sherlockDescription,
                R.drawable.sherlock,
                R.string.genreMistery
            )
        )
        moviesList.add(
            Movie(
                8,
                Date(System.currentTimeMillis() - 700000000000L),
                R.string.theSinnerTitle,
                R.string.theSinnerDescription,
                R.drawable.sinner,
                R.string.genreMistery
            )
        )
        moviesList.add(
            Movie(
                9,
                Date(System.currentTimeMillis() - 800000000000L),
                R.string.breakingBadTitle,
                R.string.breakingBadDescription,
                R.drawable.breaking_bad,
                R.string.genreAction
            )
        )
        moviesList.add(
            Movie(
                10,
                Date(System.currentTimeMillis() - 900000000000L),
                R.string.blackWidowTitle,
                R.string.blackWidowDescription,
                R.drawable.black_widow,
                R.string.genreAction
            )
        )
    }
}