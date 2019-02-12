package bkm.com.newsapp.utilities;

import android.content.Context;

import bkm.com.newsapp.data.NewsRepository;
import bkm.com.newsapp.data.database.NewsDatabase;
import bkm.com.newsapp.data.network.NewsNetworkDataSource;
import bkm.com.newsapp.ui.list.MainViewModelFactory;

public class InjectorUtils {

    public static NewsRepository provideRepository(Context context) {
        NewsDatabase database = NewsDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        NewsNetworkDataSource networkDataSource = NewsNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return NewsRepository.getInstance(database.newsDao(), networkDataSource, executors);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        NewsRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

    public static NewsNetworkDataSource provideNetworkDataSource(Context context) {
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return NewsNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }
}
