package io.github.imruahmed.noswipe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.util.ArrayList;

public class GalleryPagerActivity extends FragmentActivity {

    Context context;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private static ArrayList<PhotoItem> selectedPhotoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_pager);

        context = getBaseContext();

        Intent intent = getIntent();

        try {
            selectedPhotoItems = intent.getParcelableArrayListExtra("SELECTED_PHOTOS");
        } catch (Exception e) {
        }

        Log.v("LOOK HERE", selectedPhotoItems.toString());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void onBackPressed() { }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if(keyCode == KeyEvent.KEYCODE_BACK) {
//            KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//            Intent i = km.createConfirmDeviceCredentialIntent("No Swipe!", "");
//
//            startActivityForResult(i, 10);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10) {
            if(resultCode == Activity.RESULT_OK){
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private class GalleryPagerAdapter extends FragmentStatePagerAdapter {

        public GalleryPagerAdapter(FragmentManager fragManager) {
            super(fragManager);
        }

        @Override
        public Fragment getItem(int position) {

            GalleryPageFragment page = new GalleryPageFragment();

            try {
                page.item = selectedPhotoItems.get(position);
                page.context = context;
            } catch (Exception e){ }

            return page;
        }

        @Override
        public int getCount() {

            int count = 1;

            try {
                count = selectedPhotoItems.size();
            } catch (Exception e) {}

            return count;
        }
    }
}
