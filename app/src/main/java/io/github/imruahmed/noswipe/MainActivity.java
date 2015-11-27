package io.github.imruahmed.noswipe;

import android.content.Context;
import android.content.Intent;
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

        new AsyncHttpTask().execute();
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
        intent.putParcelableArrayListExtra("SELECTED_PHOTOS", selectedPhotoItems);

        startActivity(intent);

        selectedPhotoItems.clear();
    }

    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<Void, Void, Integer> {

        public static final int IMAGE_RESOLUTION = 15;

        // Buckets where we are fetching images from.
        public final String CAMERA_IMAGE_BUCKET_NAME =
                Environment.getExternalStorageDirectory().getAbsolutePath().toString()
                        + "/DCIM/.thumbnails";

        public final String CAMERA_IMAGE_BUCKET_ID =
                getBucketId(CAMERA_IMAGE_BUCKET_NAME);


        @Override
        protected Integer doInBackground(Void... voids) {

            final String[] projection = {MediaStore.Images.Thumbnails.DATA, MediaStore.Images.Thumbnails.IMAGE_ID};

            Cursor thumbnailsCursor = context.getContentResolver().query(
                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                    projection, // Which columns to return
                    null,       // Return all rows
                    null,
                    MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);

            // Extract the proper column thumbnails
            int thumbnailColumnIndex = thumbnailsCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

            ArrayList<PhotoItem> result = new ArrayList<PhotoItem>(thumbnailsCursor.getCount());

            if (thumbnailsCursor.moveToFirst()) {
                do {
                    // Generate a tiny thumbnail version.
                    int thumbnailImageID = thumbnailsCursor.getInt(thumbnailColumnIndex);
                    String thumbnailPath = thumbnailsCursor.getString(thumbnailImageID);
                    String fullImagePath = getFullImagePath(thumbnailsCursor, context);

                    PhotoItem newItem = new PhotoItem(thumbnailPath, fullImagePath);
                    result.add(newItem);

                } while (thumbnailsCursor.moveToNext());
            }
            thumbnailsCursor.close();

            int n = result.size();

            for (int i=0; i<result.size(); i++){
                allPhotoItems.add(result.get(n - 1 - i));
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (result == 1) {
                photoAdapter.setGridData(allPhotoItems);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);

        }
    }

    private static String getFullImagePath(Cursor thumbnailsCursor, Context context){

        String imageId = thumbnailsCursor.getString(
                thumbnailsCursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));

        // Request imageView related to this thumbnail
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor imagesCursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                filePathColumn,
                MediaStore.Images.Media._ID + "=?",
                new String[]{imageId}, null);

        if (imagesCursor != null && imagesCursor.moveToFirst()) {
            int columnIndex = imagesCursor.getColumnIndex(filePathColumn[0]);
            String filePath = imagesCursor.getString(columnIndex);
            imagesCursor.close();
            return filePath;
        } else {
            imagesCursor.close();
            return "";
        }
    }

    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }
}
