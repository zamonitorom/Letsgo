package com.letsgoapp.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.letsgoapp.Adapters.FullScreenImageAdapter;
import com.letsgoapp.R;

import java.util.ArrayList;

/**
 * Created by normalteam on 09.04.17.
 */

public class FullScreenViewActivity extends Activity {

    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulscreen);

        viewPager = (ViewPager) findViewById(R.id.pager);


        Bundle b=this.getIntent().getExtras();
        String[] array=b.getStringArray("key");

        ArrayList<String> images = new ArrayList<>();
        for (String s:array) {
            images.add(s);
        }

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,images);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(0);
    }
}
