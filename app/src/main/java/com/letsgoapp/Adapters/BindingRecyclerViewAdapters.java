package com.letsgoapp.Adapters;

import android.app.Activity;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.letsgoapp.R;
import com.letsgoapp.Utils.ContextUtil;
import com.letsgoapp.Utils.LayoutManagers;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingRecyclerViewAdapters {

    @BindingAdapter({"onClick"})
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }

    @BindingAdapter({"imageURL"})
    public static void bindImage(final ImageView view, final String url) {
        Log.d("imageURL","start");
        Log.d("imageURL","url = "+url);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.cast_mini_controller_progress_drawable)
                .into(view);

    }

    @BindingAdapter({"image"})
    public static void bindImage(ImageView view, Bitmap bitmap) {
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
        }
    }

    @BindingAdapter({"items", "itemLayout", "variable"})
    public static void setAdapter(RecyclerView recyclerView, final ObservableList items, int layoutId, int brVarId) {
        BindingRecyclerViewAdapter adapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter == null) {

            adapter = new BindingRecyclerViewAdapter(brVarId, layoutId);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
        adapter.setItems(items);
        final BindingRecyclerViewAdapter finalAdapter = adapter;

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

}
