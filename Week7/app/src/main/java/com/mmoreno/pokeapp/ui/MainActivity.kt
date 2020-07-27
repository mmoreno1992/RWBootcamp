package com.mmoreno.pokeapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.mmoreno.pokeapp.PokeApp
import com.mmoreno.pokeapp.R
import com.mmoreno.pokeapp.model.PokeEntity
import com.mmoreno.pokeapp.ui.paging.PokeBoundaryCallback
import com.mmoreno.pokeapp.ui.paging.PokePagedListAdapter
import com.mmoreno.pokeapp.viewmodel.MainViewModel
import com.mmoreno.pokeapp.workers.DownloadDataWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


/**
 * Main Activity for displaying a RecyclerView
 * with a big list of Pokemons
 */
class MainActivity : AppCompatActivity(), PokeBoundaryCallback.RecyclerViewInteractionListener {

    private val adapter by lazy(::PokePagedListAdapter)

    private val viewModel: MainViewModel by viewModels()


    /**
     * Overriding onCreate method
     * @param [savedInstanceState]
     * @return [Unit]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null)
            setUpWorker()

        setContentView(R.layout.activity_main)
        recyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        val liveData = viewModel.initializedPagedListBuilder().build()
        liveData.observe(this, Observer<PagedList<PokeEntity>> { pagedList ->
            adapter.submitList(pagedList)
        })

        viewModel.showLoading.observe(this,
            Observer { visibility ->
                progressBar.visibility = visibility
            })
    }

    /**
     * Changing the progress bar visibility
     */
    override fun changeLoadingVisibility(visibility: Int) {
        viewModel.showLoading.postValue(visibility)
    }


    //I implemented this code just for manual testing
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionNotification) {
            sendDataSyncNotification(applicationContext)
            return true
        }
        return false
    }
*/


    private fun setUpWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val work = PeriodicWorkRequestBuilder<DownloadDataWorker>(2, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(PokeApp.workName, ExistingPeriodicWorkPolicy.KEEP, work)

    }

}

