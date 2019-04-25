package bkm.com.newsapp.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import bkm.com.newsapp.data.NewsRepository
import bkm.com.newsapp.data.database.entities.NewsEntry

class MainActivityViewModel(mRepository: NewsRepository) : ViewModel() {

    var news: LiveData<List<NewsEntry>> = mRepository.currentNews
}