package bkm.com.newsapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bkm.com.newsapp.data.NewsRepository

class MainViewModelFactory(val newsRepository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(newsRepository) as T
    }
}