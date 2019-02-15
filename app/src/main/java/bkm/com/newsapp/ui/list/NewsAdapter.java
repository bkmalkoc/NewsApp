package bkm.com.newsapp.ui.list;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;

import java.util.List;

import bkm.com.newsapp.R;
import bkm.com.newsapp.data.database.entities.NewsEntry;
import bkm.com.newsapp.ui.adapters.MyBaseAdapter;
import bkm.com.newsapp.ui.adapters.MyBaseViewHolder;

public class NewsAdapter extends MyBaseAdapter {
    private List<NewsEntry> mNewsEntries;

    public NewsAdapter(List<NewsEntry> data) {
        this.mNewsEntries = data;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return mNewsEntries.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int layoutId) {
        return R.layout.news_list_item;
    }

    @Override
    public int getItemCount() {
        if (null == mNewsEntries) return 0;
        return mNewsEntries.size();
    }

//    @BindingAdapter({"imageUrl", "error"})
//    public static void loadImage() {
//
//    }

    void swapNews(final List<NewsEntry> newsEntries) {
        mNewsEntries = newsEntries;
        notifyDataSetChanged();
        if (newsEntries == null) {
            mNewsEntries = newsEntries;
            notifyDataSetChanged();
        } else {
            mNewsEntries = newsEntries;
        }

    }
}
