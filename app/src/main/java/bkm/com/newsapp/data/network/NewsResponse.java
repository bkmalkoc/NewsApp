package bkm.com.newsapp.data.network;

import androidx.annotation.NonNull;

import bkm.com.newsapp.data.database.entities.NewsEntry;

public class NewsResponse {

    @NonNull
    private final NewsEntry[] mNews;

    public NewsResponse(@NonNull NewsEntry[] mNews) {
        this.mNews = mNews;
    }

    public NewsEntry[] getNews() {
        return mNews;
    }
}
