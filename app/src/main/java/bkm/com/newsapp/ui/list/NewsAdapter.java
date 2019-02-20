package bkm.com.newsapp.ui.list;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bkm.com.newsapp.R;
import bkm.com.newsapp.data.database.entities.NewsEntry;
import bkm.com.newsapp.ui.adapters.MyBaseAdapter;

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

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .fit()
                .placeholder(R.drawable.tv_placeholder)
                .into(view);
    }

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
