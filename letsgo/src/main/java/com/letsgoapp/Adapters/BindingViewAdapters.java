package com.letsgoapp.Adapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.ObservableList;

import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.R;

import com.letsgoapp.Utils.CircleTransform;
import com.letsgoapp.Utils.ExtendedEditText;
import com.letsgoapp.Utils.LayoutManagers;

import com.letsgoapp.Utils.CropTransformation;
import com.letsgoapp.ViewModels.MainActivityViewModel;
import com.letsgoapp.databinding.NavHeaderMainBinding;
import com.squareup.picasso.Picasso;

public class BindingViewAdapters {

    @BindingAdapter({"onClick"})
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }

    @BindingAdapter({"imageURL"})
    public static void bindImage(final ImageView view, final String url) {
        Log.d("imageURL", "start");
        Log.d("imageURL", "url = " + url);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.cast_mini_controller_progress_drawable)
                .into(view);

    }

    @BindingAdapter({"imageURL2"})
    public static void bindImage2(final ImageView view, final String url) {
        Log.d("imageURL", "start");
        Log.d("imageURL", "url = " + url);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.cast_mini_controller_progress_drawable)
                .transform(new CircleTransform())
                .into(view);

    }

    @BindingAdapter({"imageURL3"})
    public static void bindImage3(final ImageView view, final String url) {
        Log.d("imageURL3", "start");
        Log.d("imageURL3", "url = " + url);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.with(view.getContext())
                .load(url)
                .transform(new CropTransformation(0,0,240,320))
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

    @BindingAdapter({"binding"})
    public static void bindEditText(ExtendedEditText view, final MyObservableString observableString) {
        view.setTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                observableString.set(s.toString());
            }
        });

        String newValue = observableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }

    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }


    @BindingConversion
    public static String convertObservableStringToString(MyObservableString observableString) {
        return observableString.get();
    }

    @BindingAdapter({"model"})
    public static void loadHeader(NavigationView view, MainActivityViewModel model) {
        NavHeaderMainBinding binding = NavHeaderMainBinding.inflate(LayoutInflater.from(view.getContext()));
        binding.setMainVM(model);
        binding.executePendingBindings();
        view.addHeaderView(binding.getRoot());
    }

}
