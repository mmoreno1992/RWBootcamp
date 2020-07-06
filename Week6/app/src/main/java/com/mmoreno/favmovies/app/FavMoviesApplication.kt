package com.mmoreno.favmovies.app

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mmoreno.favmovies.R
import com.mmoreno.favmovies.model.Movie
import com.mmoreno.favmovies.model.MoviesDatabase
import com.mmoreno.favmovies.model.concurrency.ioThread
import java.util.*

/**
 * Class extending Application
 * for pre-populating the database
 * @author Mario
 */
class FavMoviesApplication : Application() {

    /**
     * Variable for accessing the database instance in
     * the repository
     */
    companion object {
        lateinit var database: MoviesDatabase
    }

    /**
     * Overriding default onCreate method
     * Creating the database instance IF NOT EXISTS
     * And adding callback for inserting the data
     * @return [Unit]
     */
    override fun onCreate() {
        super.onCreate()
        database =
            Room.databaseBuilder(
                this,
                MoviesDatabase::class.java, "movies_database.db"
            )
                .addCallback(roomDatabaseCallback)
                .build()
    }

    /**
     * Custom RoomDatabase.Callback for inserting data to the database
     * @return [Unit]
     */
    private val roomDatabaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val movieDao = database.movieDao()

            ioThread {
                movieDao.insertAll(
                    Movie(
                        1,
                        Date(System.currentTimeMillis() - 120000000),
                        "Joker",
                        """
                          In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. 
                          He then embarks on a downward spiral of revolution and bloody crime. 
                          This path brings him face-to-face with his alter-ego: the Joker.  
                        """.trimIndent(),
                        R.drawable.joker,
                        applicationContext.getString(R.string.genreDrama)
                    ),
                    Movie(
                        2,
                        Date(System.currentTimeMillis() - 3400000000000L),
                        "End Game",
                        """
                            After the devastating events of Vengadores: Infinity War (2018), the universe is in ruins. 
                            With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' 
                            actions and restore balance to the universe.
                        """.trimIndent(),
                        R.drawable.end_game,
                        applicationContext.getString(
                            R.string.genreAction
                        )
                    ),
                    Movie(
                        3,
                        Date(System.currentTimeMillis() - 350000000000L),
                        "Coco",
                        """
                            Aspiring musician Miguel, confronted with his family\'s ancestral ban on music, 
                            enters the Land of the Dead to find his great-great-grandfather, a legendary singer.
                        """.trimIndent(),
                        R.drawable.coco,
                        applicationContext.getString(R.string.genreComedy)
                    ),
                    Movie(
                        4,
                        Date(System.currentTimeMillis() - 360000000000L),
                        "Bad Boys For Life",
                        """
                            Miami detectives Mike Lowrey and Marcus Burnett must face off against a 
                            mother-and-son pair of drug lords who wreak vengeful havoc on their city.
                        """.trimIndent(),
                        R.drawable.bad_boys,
                        applicationContext.getString(R.string.genreAction)
                    ),
                    Movie(
                        5,
                        Date(System.currentTimeMillis() - 400000000000L),
                        "Jumanji : The Next Level",
                        """
                            In Jumanji: The Next Level, the gang is back but the game has changed. 
                            As they return to rescue one of their own, the players will have to 
                            brave parts unknown from arid deserts to snowy mountains, to escape
                             the world\'s most dangerous game.
                        """.trimIndent(),
                        R.drawable.jumanji,
                        applicationContext.getString(R.string.genreAction)
                    ),
                    Movie(
                        6,
                        Date(System.currentTimeMillis() - 500000000000L),
                        "Hannibal",
                        """
                            Explores the early relationship between the renowned psychiatrist and 
                            his patient, a young FBI criminal profiler, who is haunted by his 
                            ability to empathize with serial killers.
                        """.trimIndent(),
                        R.drawable.hannibal,
                        applicationContext.getString(R.string.genreHorror)
                    ),
                    Movie(
                        7,
                        Date(System.currentTimeMillis() - 600000000000L),
                        "Sherlock",
                        """
                            A modern update finds the famous sleuth and his doctor partner 
                            solving crime in 21st century London.
                        """.trimIndent(),
                        R.drawable.sherlock,
                        applicationContext.getString(R.string.genreMistery)
                    ),
                    Movie(
                        8,
                        Date(System.currentTimeMillis() - 700000000000L),
                        "The Sinner",
                        """
                           Anthology series that examines how and why ordinary people 
                           commit brutal crimes.
                       """.trimIndent(),
                        R.drawable.sinner,
                        applicationContext.getString(R.string.genreMistery)
                    ),
                    Movie(
                        9,
                        Date(System.currentTimeMillis() - 800000000000L),
                        "Breaking Bad",
                        """
                            A high school chemistry teacher diagnosed with inoperable lung 
                            cancer turns to manufacturing and selling methamphetamine in 
                            order to secure his family's future.
                        """.trimIndent(),
                        R.drawable.breaking_bad,
                        applicationContext.getString(R.string.genreAction)
                    ),
                    Movie(
                        10,
                        Date(System.currentTimeMillis() - 900000000000L),
                        "Black Widow",
                        """
                            A film about Natasha Romanoff in her quests between the films
                             Civil War and Infinity War.
                        """.trimIndent(),
                        R.drawable.black_widow,
                        applicationContext.getString(R.string.genreAction)
                    )
                )

            }

        }
    }

}