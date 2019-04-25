package bkm.com.newsapp.ui.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import bkm.com.newsapp.data.NewsRepository

class MainViewModelFactory(val newsRepository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(newsRepository) as T
    }
}