package io.github.imruahmed.noswipe;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoImageLoader extends AsyncTaskLoader<ArrayList<PhotoItem>> {

    ArrayList<PhotoItem> photoItems;

    Context context;

    public static final int IMAGE_RESOLUTION = 15;

    public static final String CAMERA_IMAGE_BUCKET_NAME = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath()
            .toString() + "/DCIM/Camera";

    public static final String CAMERA_IMAGE_BUCKET_ID =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    public PhotoImageLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<PhotoItem> loadInBackground() {

        photoItems = new ArrayList<>();
        ArrayList<PhotoItem> loadedPhotos = getAlbumThumbnails(context);

        int n = loadedPhotos.size();

        for(int i=0; i<n; i++){
            photoItems.add(loadedPhotos.get(n-1-i));
        }

        return photoItems;
    }

    @Override
    public void deliverResult(ArrayList<PhotoItem> data) {
        super.deliverResult(data);
    }

    public static ArrayList<PhotoItem> getAlbumThumbnails(Context context) {


        final String[] projection = {MediaStore.Images.Thumbnails.DATA, MediaStore.Images.Thumbnails.IMAGE_ID};

        Cursor thumbnailsCursor = context.getContentResolver().query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, // Which columns to return
                null,       // Return all rows
                null,
                MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);

        // Extract the proper column thumbnails
        int thumbnailColumnIndex = thumbnailsCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
        int index = 0;

        ArrayList<PhotoItem> result = new ArrayList<PhotoItem>(thumbnailsCursor.getCount());

        if (thumbnailsCursor.moveToFirst()) {
            do {
                int thumbnailImageID = thumbnailsCursor.getInt(thumbnailColumnIndex);
                String thumbnailPath = "file://"+thumbnailsCursor.getString(thumbnailImageID);
                String fullImagePath = "file://"+getFullImagePath(thumbnailsCursor, context);

                PhotoItem newItem = new PhotoItem(thumbnailPath, fullImagePath, index);
                result.add(newItem);

                index++;

            } while (thumbnailsCursor.moveToNext());
        }
        thumbnailsCursor.close();

        return result;

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
