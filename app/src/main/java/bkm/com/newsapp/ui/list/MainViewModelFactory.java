package bkm.com.newsapp.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import bkm.com.newsapp.data.NewsRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final NewsRepository mRepository;

    public MainViewModelFactory(NewsRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(mRepository);
    }
}
