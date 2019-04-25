package bkm.com.newsapp.utilities

import android.content.Context
import bkm.com.newsapp.data.NewsRepository
import bkm.com.newsapp.data.database.NewsDatabase
import bkm.com.newsapp.data.network.NewsNetworkDataSource
import bkm.com.newsapp.ui.list.MainViewModelFactory

class InjectorUtils {
    companion object {
        fun provideRepository(context: Context): NewsRepository {
            var database = NewsDatabase.getInstance(context.applicationContext)
            var executors = AppExecutors.getInstance()
            var networkDataSource = NewsNetworkDataSource.getInstance(context, executors)
            return NewsRepository.getInstance(database.newsDao(), networkDataSource, executors)
        }

        fun provideMainActivityViewModelFactory(context: Context): MainViewModelFactory {
            var repository = provideRepository(context.applicationContext)
            return MainViewModelFactory(repository)
        }

        fun provideNetworkDataSource(context: Context): NewsNetworkDataSource? {
            provideRepository(context.applicationContext)
            var executors = AppExecutors.getInstance()
            return NewsNetworkDataSource.getInstance(context.applicationContext, executors)
        }
    }
}