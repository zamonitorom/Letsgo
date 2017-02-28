package com.letsgoapp.ViewModels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.letsgoapp.Utils.ContextUtil.GetContextApplication;
import static com.letsgoapp.Utils.ContextUtil.GetTopContext;

/**
 * Created by normalteam on 15.02.17.
 */

public class PhotoItemViewModel extends BaseObservable {

    private String link;
    private Bitmap bitmap;

    public PhotoItemViewModel(String link) {
        if (link != null) {
            this.link = link;
            //loadBitmap(link);
        }
    }

    @Bindable
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        notifyPropertyChanged(BR.link);
    }

    public void loadBitmap(String link) {
        Picasso.with((Context) GetTopContext())
                .load(link)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d("PhotoItemViewModel", "onBitmapLoaded");
                        setBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d("PhotoItemViewModel", "onBitmapFailed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Log.d("PhotoItemViewModel", "onPrepareLoad");
                    }
                });
    }

    @Bindable
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        notifyPropertyChanged(BR.bitmap);
    }
}
