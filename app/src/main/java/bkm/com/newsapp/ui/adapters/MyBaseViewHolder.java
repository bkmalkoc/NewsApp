package bkm.com.newsapp.ui.adapters;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import bkm.com.newsapp.BR;

public class MyBaseViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public MyBaseViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
    }
}