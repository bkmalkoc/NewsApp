package bkm.com.newsapp.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import bkm.com.newsapp.utilities.InjectorUtils;

public class NewsSyncIntentService extends IntentService {
    private static final String LOG_TAG = NewsSyncIntentService.class.getSimpleName();

    public NewsSyncIntentService() {
        super("NewsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(LOG_TAG, "Intent service started");

        NewsNetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchNews();
    }
}
