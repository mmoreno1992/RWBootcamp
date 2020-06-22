package com.mmoreno.favmovies.model

import com.mmoreno.favmovies.R
import java.time.LocalDate
import java.util.*

object DataRepository {
    val moviesList = mutableListOf<Movie>()

    init {
        moviesList.add(
            Movie(
                1,
                Date(),
                "Joker",
                R.string.jokerDescription,
                R.drawable.joker
            )
        )
        moviesList.add(
            Movie(
                2,
                Date(),
                "End Game",
                R.string.endGameDescription,
                R.drawable.end_game
            )
        )
        moviesList.add(
            Movie(
                3,
                Date(),
                "Coco",
                R.string.cocoDescription,
                R.drawable.coco
            )
        )
        moviesList.add(
            Movie(
                4,
                Date(),
                "Bad Boys For Life",
                R.string.badBoysDescription,
                R.drawable.bad_boys
            )
        )
        moviesList.add(
            Movie(
                5,
                Date(),
                "Jumanji : The Next Level",
                R.string.jumanjiNextLevelDescription,
                R.drawable.jumanji
            )
        )
        moviesList.add(
            Movie(
                6,
                Date(),
                "Hannibal",
                R.string.hannibalDescription,
                R.drawable.hannibal
            )
        )
        moviesList.add(
            Movie(
                7,
                Date(),
                "Sherlock",
                R.string.sherlockDescription,
                R.drawable.sherlock
            )
        )
        moviesList.add(
            Movie(
                8,
                Date(),
                "The Sinner",
                R.string.theSinnerDescription,
                R.drawable.sinner
            )
        )
        moviesList.add(
            Movie(
                9,
                Date(),
                "Breaking Bad",
                R.string.breakingBadDescription,
                R.drawable.sinner
            )
        )
    }
}