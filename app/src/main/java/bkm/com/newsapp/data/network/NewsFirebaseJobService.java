package bkm.com.newsapp.data.network;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import bkm.com.newsapp.utilities.InjectorUtils;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NewsFirebaseJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        NewsNetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchNews();
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
