package bkm.com.newsapp.data.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.util.Log
import bkm.com.newsapp.data.database.entities.NewsEntry
import bkm.com.newsapp.utilities.AppExecutors
import com.firebase.jobdispatcher.*
import java.util.concurrent.TimeUnit

class NewsNetworkDataSource(val context: Context, val executors: AppExecutors) {

    companion object {
        private val LOG_TAG = NewsNetworkDataSource::class.java.simpleName
        private val NEWS_SYNC_TAG = "NEWS-SYNC"
        private val SYNC_INTERVAL_HOURS = 3
        private val SYNC_INTERVAL_SECONDS = TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS.toLong()).toInt()
        private val SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3

        private val LOCK = Any()
        private var sInstance: NewsNetworkDataSource? = null

        fun getInstance(context: Context, executors: AppExecutors): NewsNetworkDataSource? {
            Log.d(LOG_TAG, "Getting the network data source")
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = NewsNetworkDataSource.getInstance(context.applicationContext, executors)
                    Log.d(LOG_TAG, "Made new network data source")
                }
            }
            return sInstance
        }
    }

    private val mDownloadedNews = MutableLiveData<Array<NewsEntry>>()

    fun startFetchNewsSercice() {
        var intentToFetch = Intent(context, NewsSyncIntentService::class.java)
        context.startService(intentToFetch)
        Log.d(LOG_TAG, "Service created")
    }

    fun scheduleRecurringFetchNewsSync() {
        var driver = GooglePlayDriver(context)
        var dispatcher = FirebaseJobDispatcher(driver)

        var syncNewsJob = dispatcher.newJobBuilder()
                .setService(NewsFirebaseJobService::class.java)
                .setTag(NEWS_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS, SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build()
        dispatcher.schedule(syncNewsJob)
        Log.d(LOG_TAG, "Job scheduled")
    }

    fun getCurrentNews(): LiveData<Array<NewsEntry>> = mDownloadedNews

    fun fetchNews() {
        Log.d(LOG_TAG, "Fetch weather started")
        executors.networkIO().execute(Runnable {
            try {
                val weatherRequestUrl = NetworkUtils.getUrl

                // Use the URL to retrieve the JSON
                val jsonNewsResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl)

                val response = NewsJsonParser().parse(jsonNewsResponse)
                Log.d(LOG_TAG, "JSON Parsing finished")


                if (response != null && response.news.isNotEmpty()) {
                    Log.d(LOG_TAG, "JSON not null and has " + response.news.size
                            + " values")

                    mDownloadedNews.postValue(response.news)
                    // Will eventually do something with the downloaded data
                }
            } catch (e: Exception) {
                // Server probably invalid
                e.printStackTrace()
            }
        })
    }

}