package com.letsgoapp.ViewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.Toolbar;

import com.letsgoapp.BR;
/**
 * Created by normalteam on 02.02.17.
 */

public class ToolbarViewModel extends BaseObservable{
    private String toolbarTitle;
    private Toolbar toolbar;

    public ToolbarViewModel() {

    }

    @Bindable
    public String getToolbarTitle() {
        return toolbarTitle;
    }

    public void setToolbarTitle(String toolbarTitle) {
        if(toolbar!=null) {
            this.toolbarTitle = toolbarTitle;
            this.toolbar.setTitle(toolbarTitle);
            notifyPropertyChanged(BR.toolbarTitle);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }
}
