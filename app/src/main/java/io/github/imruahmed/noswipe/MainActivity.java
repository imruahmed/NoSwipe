package io.github.imruahmed.noswipe;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getBaseContext();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        noSwipeButton = (Button) findViewById(R.id.noSwipeBtn);
        gallery = (GridView) findViewById(R.id.gallery);

        selectedPhotoItems = new ArrayList<>();
        allPhotoItems = new ArrayList<>();

        photoAdapter = new PhotoAdapter(this, R.layout.gallery_item, allPhotoItems);
        gallery.setAdapter(photoAdapter);
        gallery.setOnItemClickListener(gridItemClickListener);

        getLoaderManager().initLoader(R.id.loader_id, null, loaderCallbacks);
        progressBar.setVisibility(View.VISIBLE);

    }

    AdapterView.OnItemClickListener gridItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PhotoItem item = (PhotoItem) parent.getItemAtPosition(position);

            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_out);
            view.startAnimation(animation);

            selectedPhotoItems.add(item);

        }
    };

    public void startSwiping(View view) {

        Intent intent = new Intent(MainActivity.this, GalleryPagerActivity.class);

        Collections.sort(selectedPhotoItems);

        intent.putParcelableArrayListExtra("SELECTED_PHOTOS", selectedPhotoItems);

        startActivity(intent);

        selectedPhotoItems.clear();
    }

    private void updateAllPhotoItems (ArrayList<PhotoItem> photoItems) {

        allPhotoItems.clear();

        for(int i = 0; i < photoItems.size(); i++){
            PhotoItem item = photoItems.get(i);
            allPhotoItems.add(item);
        }

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

                    updateAllPhotoItems(photoItems);
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onLoaderReset(Loader<ArrayList<PhotoItem>> loader) { }
            };
}
