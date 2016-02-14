package io.github.imruahmed.noswipe;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
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

        context = getApplicationContext();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        noSwipeButton = (Button) findViewById(R.id.noSwipeBtn);
        gallery = (GridView) findViewById(R.id.gallery);

        multiSelectOn = false;

        selectedPhotoItems = new ArrayList<>();
        allPhotoItems = new ArrayList<>();
        views = new ArrayList<>();

        photoAdapter = new PhotoAdapter(this, R.layout.gallery_item, allPhotoItems);
        gallery.setAdapter(photoAdapter);

        getLoaderManager().initLoader(R.id.loader_id, null, loaderCallbacks);
        progressBar.setVisibility(View.VISIBLE);

    }

    public void startSwiping(View view) {

        if (selectedPhotoItems.isEmpty()) {
            Toast.makeText(context, "Select a picture", Toast.LENGTH_SHORT).show();
        } else {

            Collections.sort(selectedPhotoItems);

            multiSelectOn = false;

            Intent intent = new Intent(MainActivity.this, GalleryPagerActivity.class);
            intent.putParcelableArrayListExtra("SELECTED_PHOTOS", selectedPhotoItems);

            startActivity(intent);

            selectedPhotoItems.clear();

            for (int i = 0; i < views.size(); i++) {
                views.get(i).setScaleY(1f);
                views.get(i).setScaleX(1f);
            }
            views.clear();


        }
    }

    private void updateAllPhotoItems(ArrayList<PhotoItem> photoItems) {
        photoAdapter.clear();
        for (int i = 0; i < photoItems.size(); i++) {
            photoAdapter.add(photoItems.get(i));
        }
        photoAdapter.notifyDataSetChanged();
    }

    private LoaderManager.LoaderCallbacks<ArrayList<PhotoItem>> loaderCallbacks =
            new LoaderManager.LoaderCallbacks<ArrayList<PhotoItem>>() {

                @Override
                public Loader<ArrayList<PhotoItem>> onCreateLoader(int i, Bundle bundle) {
                    return new PhotoImageLoader(context);
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<PhotoItem>> loader, ArrayList<PhotoItem> photoItems) {
                    updateAllPhotoItems(photoItems);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<PhotoItem>> loader) {
                }
            };

}
