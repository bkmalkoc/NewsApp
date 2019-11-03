package bkm.com.newsapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bkm.com.newsapp.data.NewsRepository
import bkm.com.newsapp.data.database.entities.NewsEntry

class MainActivityViewModel(mRepository: NewsRepository) : ViewModel() {

    var news: LiveData<List<NewsEntry>> = mRepository.currentNews
}