package bkm.com.newsapp.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import bkm.com.newsapp.R;
import bkm.com.newsapp.data.database.entities.NewsEntry;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.getNews().observe(this, new Observer<List<NewsEntry>>() {
            @Override
            public void onChanged(@Nullable List<NewsEntry> newsEntries) {
                if(newsEntries != null) populateData(newsEntries);
            }
        });
    }

    private void populateData(List<NewsEntry> newsEntries) {
    }
}
