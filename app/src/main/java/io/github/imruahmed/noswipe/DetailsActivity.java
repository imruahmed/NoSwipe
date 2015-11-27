package io.github.imruahmed.noswipe;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class DetailsActivity extends FragmentActivity {

    Context context;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private static ArrayList<PhotoItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = getBaseContext();

        Intent intent = getIntent();

        try {
            data = intent.getParcelableArrayListExtra("SELECTED");
        } catch (Exception e) {
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private class GalleryPagerAdapter extends FragmentStatePagerAdapter {

        public GalleryPagerAdapter(FragmentManager fragManager) {
            super(fragManager);
        }

        @Override
        public Fragment getItem(int position) {

            GalleryPageFragment page = new GalleryPageFragment();

            try {
                page.item = data.get(position);
                page.context = context;
            } catch (Exception e){ }

            return page;
        }

        @Override
        public int getCount() {

            int count = 5;

            try {
                count = data.size();
            } catch (Exception e) {

            }

            return count;
        }
    }
}
