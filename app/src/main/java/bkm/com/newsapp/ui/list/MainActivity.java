package bkm.com.newsapp.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import bkm.com.newsapp.R;
import bkm.com.newsapp.data.database.entities.NewsEntry;
import bkm.com.newsapp.databinding.ActivityMainBinding;
import bkm.com.newsapp.ui.detail.DetailActivity;
import bkm.com.newsapp.utilities.Constants;
import bkm.com.newsapp.utilities.InjectorUtils;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterListener {

    MainActivityViewModel mViewModel;
    private ActivityMainBinding mMainBinding;

    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpUI();
        mViewModel.getNews().observe(this, new Observer<List<NewsEntry>>() {
            @Override
            public void onChanged(@Nullable List<NewsEntry> newsEntries) {
                if (newsEntries != null) populateData(newsEntries);
            }
        });
    }

    private void setUpUI() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMainBinding.recyclerviewNews.setLayoutManager(layoutManager);
        mMainBinding.recyclerviewNews.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(null, this);
        mMainBinding.recyclerviewNews.setAdapter(mNewsAdapter);
        showLoading();
    }

    private void populateData(List<NewsEntry> newsEntries) {
        if (newsEntries.size() < 1) {
            return;
        }
        mNewsAdapter.swapNews(newsEntries);
        showNewsDataView();
    }

    private void showLoading() {
        mMainBinding.recyclerviewNews.setVisibility(View.INVISIBLE);
        mMainBinding.loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showNewsDataView() {
        mMainBinding.loadingIndicator.setVisibility(View.INVISIBLE);
        mMainBinding.recyclerviewNews.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClicked(NewsEntry newsEntry) {
        Toast.makeText(this, newsEntry.getAuthor(), Toast.LENGTH_SHORT).show();

        Intent newsDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
        newsDetailIntent.putExtra(Constants.NEWS_URL, newsEntry.getUrl());
        startActivity(newsDetailIntent);
    }
}
