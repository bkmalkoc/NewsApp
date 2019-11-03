package bkm.com.newsapp.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bkm.com.newsapp.BR;
import bkm.com.newsapp.R;
import bkm.com.newsapp.data.database.entities.NewsEntry;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsEntry> mNewsEntries;
    NewsAdapterListener newsAdapterListener;

    public NewsAdapter(List<NewsEntry> data, NewsAdapterListener newsAdapterListener) {
        this.mNewsEntries = data;
        this.newsAdapterListener = newsAdapterListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, viewGroup, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsAdapterViewHolder, int i) {
        Object obj = getObjForPosition(i);
        newsAdapterViewHolder.bind(obj);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected Object getObjForPosition(int position) {
        return mNewsEntries.get(position);
    }

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
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.tv_placeholder)
                .into(view);
    }

    @BindingAdapter("itemClick")
    public static void itemClick(CardView cardView, NewsEntry newsEntry) {

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

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ViewDataBinding binding;

        public NewsViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Object obj) {
            binding.setVariable(BR.obj, obj);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            newsAdapterListener.onItemClicked(mNewsEntries.get(adapterPosition));
        }
    }

    public interface NewsAdapterListener {
        void onItemClicked(NewsEntry newsEntry);
    }
}
