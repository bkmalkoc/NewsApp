package bkm.com.newsapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import bkm.com.newsapp.data.database.daos.NewsDao;
import bkm.com.newsapp.data.database.entities.NewsEntry;
import bkm.com.newsapp.data.network.NewsNetworkDataSource;
import bkm.com.newsapp.utilities.AppExecutors;

public class NewsRepository {

    private static final String LOG_TAG = NewsRepository.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static NewsRepository sInstance;
    private final NewsDao mNewsDao;
    private final NewsNetworkDataSource mNewsNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    public NewsRepository(NewsDao newsDao, NewsNetworkDataSource newsNetworkDataSource, AppExecutors executors) {
        mNewsDao = newsDao;
        mNewsNetworkDataSource = newsNetworkDataSource;
        mExecutors = executors;

        LiveData<NewsEntry[]> networkData = mNewsNetworkDataSource.getCurrentNews();
        networkData.observeForever(new Observer<NewsEntry[]>() {
            @Override
            public void onChanged(@Nullable final NewsEntry[] newsEntries) {
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        deleteOldData();
                        mNewsDao.bulkInsert(newsEntries);
                    }
                });
            }
        });
    }

    public synchronized static NewsRepository getInstance(NewsDao newsDao, NewsNetworkDataSource newsNetworkDataSource, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository(newsDao, newsNetworkDataSource, executors);
            }
        }
        return sInstance;
    }

    public synchronized void initializedData() {
        if (mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFetchNeeded()) {
                    starFetchNewsService();
                }
            }
        });
    }

    private void deleteOldData() {
    }

    private boolean isFetchNeeded() {
        return true;
    }

    private void starFetchNewsService() {
        mNewsNetworkDataSource.startFetchNewsService();
    }

    public LiveData<List<NewsEntry>> getCurrentNews() {
        initializedData();
        return mNewsDao.getTopAndBreakingNews();
    }
}
