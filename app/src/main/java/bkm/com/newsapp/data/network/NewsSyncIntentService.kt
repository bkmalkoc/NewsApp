package bkm.com.newsapp.data.network

import android.app.IntentService
import android.content.Intent
import android.util.Log
import bkm.com.newsapp.utilities.InjectorUtils

class NewsSyncIntentService : IntentService("NewsSyncIntentService") {

    companion object {
        val LOG_TAG = NewsSyncIntentService::class.simpleName
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(LOG_TAG, "Intent service started")
        var networkDataSource = InjectorUtils.Companion.provideNetworkDataSource(this.applicationContext)
        networkDataSource?.fetchNews()
    }

}