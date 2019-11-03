package bkm.com.newsapp.data.network

import android.os.Build
import androidx.annotation.RequiresApi
import bkm.com.newsapp.utilities.InjectorUtils
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NewsFirebaseJobService : JobService() {

    override fun onStopJob(params: JobParameters): Boolean {
        val networkDataSource = InjectorUtils.provideNetworkDataSource(this)
        networkDataSource?.fetchNews()
        jobFinished(params, false)
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        return false
    }
}