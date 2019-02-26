package bkm.com.newsapp.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import bkm.com.newsapp.BR;
import bkm.com.newsapp.data.NewsRepository;
import bkm.com.newsapp.data.database.entities.NewsEntry;

public class MainActivityViewModel extends ViewModel {
    private NewsRepository mRepository;
    private final LiveData<List<NewsEntry>> mNews;

    public MainActivityViewModel(NewsRepository mRepository) {
        this.mRepository = mRepository;

        mNews = mRepository.getCurrentNews();
    }

    public LiveData<List<NewsEntry>> getNews() {
        return mNews;
    }
}
