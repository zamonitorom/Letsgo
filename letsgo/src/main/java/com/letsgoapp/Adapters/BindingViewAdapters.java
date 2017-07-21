package com.letsgoapp.Adapters;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.BindingBuildInfo;
import android.databinding.BindingConversion;
import android.databinding.ObservableList;

import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.letsgoapp.Models.MyObservableString;
import com.letsgoapp.R;

import com.letsgoapp.Utils.CircleTransform;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.Utils.ExtendedEditText;
import com.letsgoapp.Utils.LayoutManagers;

import com.letsgoapp.Utils.CropTransformation;
import com.letsgoapp.Utils.OnSwipeTouchListener;
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
        try {
            Picasso.with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.cast_mini_controller_progress_drawable)
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @BindingAdapter({"imageURL2"})
    public static void bindImage2(final ImageView view, String url) {
        Log.d("imageURL2", "start");
        Log.d("imageURL2", "url = " + url);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        try {
            Picasso.with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.cast_mini_controller_progress_drawable)
                    .transform(new CircleTransform())
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @BindingAdapter({"imageURL3"})
    public static void bindImage3(final ImageView view, final String url) {
        Log.d("imageURL3", "start");
        Log.d("imageURL3", "url = " + url);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        try {
            Picasso.with(view.getContext())
                    .load(url)
//                    .transform(new CropTransformation(0, 0, 300, 400))
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @BindingAdapter({"image"})
    public static void bindImage(ImageView view, Bitmap bitmap) {
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
        }
    }


    @BindingAdapter({"items", "itemLayout", "variable","scrollOn"})
    public static void setAdapter(RecyclerView recyclerView, final ObservableList items, int layoutId, int brVarId, boolean scroll)  {
        BindingRecyclerViewAdapter adapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter == null) {

            adapter = new BindingRecyclerViewAdapter(brVarId, layoutId);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
        adapter.setItems(items);

        final BindingRecyclerViewAdapter finalAdapter = adapter;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(scroll) {
            adapter.runnable = () -> recyclerView.scrollToPosition(items.size() - 1);
        }
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

    @BindingAdapter({"bindError","errorMessage"})
    public static void bindError(TextInputLayout view, final Boolean isError,final String message) {
        view.setErrorEnabled(isError);
        if(isError) {
            view.setError(message);
        }
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("imageBitmap")
    public static void setImageResource(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
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

    @BindingAdapter({"android:year", "android:month", "android:day", "android:onDateChanged"})
    public static void setDate(DatePicker view, int year, int month, int day,
                               DatePicker.OnDateChangedListener listener) {
        view.init(year, month, day, listener);
    }

    @BindingAdapter({"android:year", "android:month", "android:day"})
    public static void setDate(DatePicker view, int year, int month, int day) {
        view.updateDate(year, month, day);
    }

    @BindingAdapter({"android:year", "android:month"})
    public static void setYearMonth(DatePicker view, int year, int month) {
        setDate(view, year, month, view.getDayOfMonth());
    }

    @BindingAdapter({"android:month", "android:day"})
    public static void setMonthDay(DatePicker view, int month, int day) {
        setDate(view, view.getYear(), month, day);
    }

    @BindingAdapter(value = {"android:year", "android:day", "android:onDateChanged"},
            requireAll = false)
    public static void setYearDay(DatePicker view, int year, int day,
                                  DatePicker.OnDateChangedListener listener) {
        setDate(view, year, view.getMonth(), day, listener);
    }

    @BindingAdapter({"android:year", "android:month", "android:onDateChanged"})
    public static void setYearMonth(DatePicker view, int year, int month,
                                    DatePicker.OnDateChangedListener listener) {
        setDate(view, year, month, view.getDayOfMonth(), listener);
    }

    @BindingAdapter({"android:month", "android:day", "android:onDateChanged"})
    public static void setMonthDay(DatePicker view, int month, int day,
                                   DatePicker.OnDateChangedListener listener) {
        setDate(view, view.getYear(), month, day, listener);
    }

    @BindingAdapter({"swipe"})
    public static void bindSwipe(View view, final Runnable runnable) {

        view.setOnTouchListener(new OnSwipeTouchListener((Activity)ContextUtill.GetTopContext()){
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                runnable.run();
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                runnable.run();
            }

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                runnable.run();
            }
        });

    }

}
