package io.github.imruahmed.noswipe;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.json.JsonFactory;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.GphotoFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class PhotoGalleryImageProvider {

    public static final int IMAGE_RESOLUTION = 15;

    // Buckets where we are fetching images from.
    public static final String CAMERA_IMAGE_BUCKET_NAME =
            Environment.getExternalStorageDirectory().toString()
                    + "/DCIM/Camera";
    public static final String CAMERA_IMAGE_BUCKET_ID =
            getBucketId(CAMERA_IMAGE_BUCKET_NAME);

    /**
     * Fetch both full sized images and thumbnails via a single query.
     * Returns all images not in the Camera Roll.
     */
    public static List<PhotoItem> getAlbumThumbnails(Context context) {

        final String[] projection = {MediaStore.Images.Thumbnails.DATA, MediaStore.Images.Thumbnails.IMAGE_ID};

        Cursor thumbnailsCursor = context.getContentResolver().query( MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, // Which columns to return
                null,       // Return all rows
                null,
                null);

        // Extract the proper column thumbnails
        int thumbnailColumnIndex = thumbnailsCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
        ArrayList<PhotoItem> result = new ArrayList<PhotoItem>(thumbnailsCursor.getCount());

        if (thumbnailsCursor.moveToFirst()) {
            do {
                // Generate a tiny thumbnail version.
                int thumbnailImageID = thumbnailsCursor.getInt(thumbnailColumnIndex);
                String thumbnailPath = thumbnailsCursor.getString(thumbnailImageID);
                Uri thumbnailUri = Uri.parse(thumbnailPath);
                Uri fullImageUri = uriToFullImage(thumbnailsCursor,context);

                // Create the list item.
                PhotoItem newItem = new PhotoItem(thumbnailUri, fullImageUri);
                result.add(newItem);
            } while (thumbnailsCursor.moveToNext());
        }
        thumbnailsCursor.close();
        return result;
    }

    /**
     * Get the path to the full image for a given thumbnail.
     */
    private static Uri uriToFullImage(Cursor thumbnailsCursor, Context context){
        String imageId = thumbnailsCursor.getString(thumbnailsCursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));

        // Request image related to this thumbnail
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor imagesCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, filePathColumn, MediaStore.Images.Media._ID + "=?", new String[]{imageId}, null);

        if (imagesCursor != null && imagesCursor.moveToFirst()) {
            int columnIndex = imagesCursor.getColumnIndex(filePathColumn[0]);
            String filePath = imagesCursor.getString(columnIndex);
            imagesCursor.close();
            return Uri.parse(filePath);
        } else {
            imagesCursor.close();
            return Uri.parse("");
        }
    }

    /**
     * Render a thumbnail photo and scale it down to a smaller size.
     */
    private static Bitmap bitmapFromPath(String path){
        File imgFile = new  File(path);
        Bitmap imageBitmap = null;

        if(imgFile.exists()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = IMAGE_RESOLUTION;
            imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
        }
        return imageBitmap;
    }

    /**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }
}