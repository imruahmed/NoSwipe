package io.github.imruahmed.noswipe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.LoaderManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    private GridView gallery;
    private ProgressBar progressBar;
    private Button noSwipeButton;

    private PhotoAdapter photoAdapter;
    public ArrayList<PhotoItem> allPhotoItems;
    private ArrayList<PhotoItem> selectedPhotoItems;

    private ArrayList<View> views;



    private boolean multiSelectOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getBaseContext();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        noSwipeButton = (Button) findViewById(R.id.noSwipeBtn);
        gallery = (GridView) findViewById(R.id.gallery);

        multiSelectOn = false;

        selectedPhotoItems = new ArrayList<>();
        allPhotoItems = new ArrayList<>();
        views = new ArrayList<>();

        photoAdapter = new PhotoAdapter(this, R.layout.gallery_item, allPhotoItems);
        gallery.setAdapter(photoAdapter);
        gallery.setOnItemClickListener(gridItemClickListener);
        gallery.setOnItemLongClickListener(gridItemLongClickListener);

        getLoaderManager().initLoader(R.id.loader_id, null, loaderCallbacks);
        progressBar.setVisibility(View.VISIBLE);

    }

    AdapterView.OnItemClickListener gridItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PhotoItem item = (PhotoItem) parent.getItemAtPosition(position);
            selectedPhotoItems.add(item);
            startSwiping(view);
        }
    };

    AdapterView.OnItemLongClickListener gridItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            PhotoItem item = (PhotoItem) adapterView.getItemAtPosition(i);

            selectedPhotoItems.add(item);
            Toast.makeText(context, "Selected: "+selectedPhotoItems.size(), Toast.LENGTH_SHORT).show();
//            if (!multiSelectOn) {
//                multiSelectOn = true;
//                selectedPhotoItems.add(item);
//                view.setScaleX(0.8f);
//                view.setScaleY(0.8f);
//            } else {
//                if (selectedPhotoItems.contains(item)) {
//                    selectedPhotoItems.remove(item);
//                    views.remove(view);
//                    view.setScaleX(1f);
//                    view.setScaleY(1f);
//                } else {
//                    selectedPhotoItems.add(item);
//                    views.add(view);
//                    view.setScaleX(0.8f);
//                    view.setScaleY(0.8f);
//                }
//                if (selectedPhotoItems.isEmpty()) {
//                    multiSelectOn = false;
//                }
//            }
//            return true;
            return true;
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startSwiping(View view) {

        if (selectedPhotoItems.isEmpty()){
            Toast.makeText(context, "Select a picture", Toast.LENGTH_SHORT).show();
        } else {

            Collections.sort(selectedPhotoItems);

            multiSelectOn = false;

            Intent intent = new Intent(MainActivity.this, GalleryPagerActivity.class);
            intent.putParcelableArrayListExtra("SELECTED_PHOTOS", selectedPhotoItems);

            startLockTask();

            startActivity(intent);

            selectedPhotoItems.clear();

            for (int i=0; i<views.size(); i++) {
                views.get(i).setScaleY(1f);
                views.get(i).setScaleX(1f);
            }
            views.clear();

        }
    }

    private void updateAllPhotoItems (ArrayList<PhotoItem> photoItems) {
        photoAdapter.setGridData(allPhotoItems);
    }

    private LoaderManager.LoaderCallbacks<ArrayList<PhotoItem>> loaderCallbacks =
            new LoaderManager.LoaderCallbacks<ArrayList<PhotoItem>>() {

                @Override
                public Loader<ArrayList<PhotoItem>> onCreateLoader(int i, Bundle bundle) {
                    return new PhotoImageLoader(context);
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<PhotoItem>> loader, ArrayList<PhotoItem> photoItems) {

                    new MyTask().execute(photoItems);
                    updateAllPhotoItems(photoItems);
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onLoaderReset(Loader<ArrayList<PhotoItem>> loader) { }
            };

    private class MyTask extends AsyncTask<ArrayList<PhotoItem>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<PhotoItem>... arrayLists) {

            ArrayList<PhotoItem> photoItems = new ArrayList<>();
            photoItems = arrayLists[0];

            allPhotoItems.clear();

            for(int i = 0; i < photoItems.size(); i++){
                PhotoItem item = photoItems.get(i);
                allPhotoItems.add(item);
            }

            return null;
        }
    }
}
