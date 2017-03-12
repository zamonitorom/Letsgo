package com.letsgoapp.ViewModels;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.letsgoapp.BR;
import com.letsgoapp.Views.ProfileActivity;
import com.letsgoapp.Views.ProfileActivity2;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

/**
 * Created by normalteam on 11.02.17.
 */

public class MainActivityViewModel extends BaseObservable {

    private String mem;
    private ToolbarViewModel toolbarViewModel;

    public MainActivityViewModel(String mem) {
        toolbarViewModel = new ToolbarViewModel();
        this.mem = mem;
    }

    @Bindable
    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
        notifyPropertyChanged(BR.mem);
    }

    public void getProfile(){
        Activity activity = (Activity) GetTopContext();
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra("link","");
        activity.startActivity(intent);
    }

    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }

    public void setToolbarViewModel(ToolbarViewModel toolbarViewModel) {
        this.toolbarViewModel = toolbarViewModel;
    }
}
