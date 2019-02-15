package bkm.com.newsapp.ui.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class MyBaseAdapter extends RecyclerView.Adapter<MyBaseViewHolder> {

    public MyBaseAdapter() {
    }

    @NonNull
    @Override
    public MyBaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, viewGroup, false);
        return new MyBaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBaseViewHolder newsAdapterViewHolder, int i) {
        Object obj = getObjForPosition(i);
        newsAdapterViewHolder.bind(obj);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract Object getObjForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);
}
