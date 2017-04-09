package com.letsgoapp.ViewModels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.letsgoapp.BR;
import com.letsgoapp.R;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;


/**
 * Created by normalteam on 15.02.17.
 */

public class PhotoItemViewModel extends BaseObservable {

    private String link;
    private Bitmap bitmap;
    private Integer position;

    public PhotoItemViewModel(String link) {
        if (link != null) {
            this.link = link;
            //loadBitmap(link);
        }
    }

    public PhotoItemViewModel(String link,Integer position) {
        if (link != null) {
            this.link = link;
            this.position = position;
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

    public void click(){
        INavigationService navigationService = new NavigationService();
        navigationService.goFullscreen(position);
    }

    @Bindable
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        notifyPropertyChanged(BR.bitmap);
    }

    @Bindable
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }
}
