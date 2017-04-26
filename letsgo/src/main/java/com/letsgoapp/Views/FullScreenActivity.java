package com.letsgoapp.Views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.letsgoapp.Adapters.FullScreenImageAdapter;
import com.letsgoapp.R;
import com.letsgoapp.Utils.ContextUtill;

/**
 * Created by normalteam on 09.04.17.
 */

public class FullScreenActivity extends Activity {

    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulscreen);

        viewPager = (ViewPager) findViewById(R.id.pager);


        Bundle b=this.getIntent().getExtras();
        String[] array=b.getStringArray("key");

        adapter = new FullScreenImageAdapter(FullScreenActivity.this, ContextUtill.GetContextApplication().getCurrentPhotos());

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(getIntent().getIntExtra("position",0));
    }
}
