package io.github.imruahmed.noswipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

public class GalleryPager extends FragmentActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private static ArrayList<View> imageList;

    public GalleryPager() {
        //imageList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery_pager);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
    }


    public static void addImage(View v) {
        imageList.add(v);
    }

    private class GalleryPagerAdapter extends FragmentStatePagerAdapter {
        public GalleryPagerAdapter(FragmentManager fragManager) {
            super(fragManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new GalleryPageFragment();
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
