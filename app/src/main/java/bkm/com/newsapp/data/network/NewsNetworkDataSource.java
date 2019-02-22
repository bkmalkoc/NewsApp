package bkm.com.newsapp.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import bkm.com.newsapp.data.database.entities.NewsEntry;
import bkm.com.newsapp.utilities.AppExecutors;

public class NewsNetworkDataSource {

    private static final String LOG_TAG = NewsNetworkDataSource.class.getSimpleName();
    private static final String NEWS_SYNC_TAG = "NEWS-SYNC";
    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static final Object LOCK = new Object();
    private static NewsNetworkDataSource sInstance;
    private final Context mContext;
    private final AppExecutors mExecutors;

    private final MutableLiveData<NewsEntry[]> mDownloadedNews;

    private NewsNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedNews = new MutableLiveData<>();
    }

    public static NewsNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public void startFetchNewsService() {
        Intent intentToFetch = new Intent(mContext, NewsSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(LOG_TAG, "Service created");
    }

    public void scheduleRecurringFetchNewsSync() {
        Driver driver = new GooglePlayDriver(mContext);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncNewsJob = dispatcher.newJobBuilder()
                .setService(NewsFirebaseJobService.class)
                .setTag(NEWS_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncNewsJob);
        Log.d(LOG_TAG, "Job scheduled");
    }

    public LiveData<NewsEntry[]> getCurrentNews() {
        return mDownloadedNews;
    }

    void fetchNews() {
        Log.d(LOG_TAG, "Fetch weather started");
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    URL weatherRequestUrl = NetworkUtils.getUrl();

                    // Use the URL to retrieve the JSON
                    String jsonNewsResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                    NewsResponse response = new NewsJsonParser().parse(jsonNewsResponse);
                    Log.d(LOG_TAG, "JSON Parsing finished");


                    if (response != null && response.getNews().length != 0) {
                        Log.d(LOG_TAG, "JSON not null and has " + response.getNews().length
                                + " values");

                        mDownloadedNews.postValue(response.getNews());
                        // Will eventually do something with the downloaded data
                    }
                } catch (Exception e) {
                    // Server probably invalid
                    e.printStackTrace();
                }
            }
        });
    }

}
