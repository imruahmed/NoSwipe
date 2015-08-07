package io.github.imruahmed.noswipe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends Activity {
    private final String LOGTAG = getClass().getName();
    private final int REQUEST_IMAGE = 33;
    private ProgressDialog progressDialog;
    private File file = null;
    private ImageView imagePreview;
    private Context mContext;

    /**
     * Usual onCreate - nothing special there
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.grid_item);
        imagePreview = (ImageView) findViewById(R.id.imagePreview);
    }

    /**
     * Button handler. Also very common one. Just create Intent and run it.
     */
    public void handleFromGallery(View _view) {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE);
    }

    /**
     * Get result from gallery. Well we extract URI from intent and run
     * ImageDisplayer
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent _intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE) {
            ImageDisplayer displayer = new ImageDisplayer();
            if(_intent.getData()!=null){
                displayer.execute(_intent.getData());
            }
        }
    }

    /**
     * This is it. here we can see, how to process Uri from gallery result
     * in a proper way to get image displayed.
     *
     */
    private class ImageDisplayer extends AsyncTask<Uri, Drawable,Drawable> {
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(mContext, "Loading", "");
        }
        @Override
        protected Drawable doInBackground(Uri... _path) {
            file = null;
            // This is the key line. Content provider client gives us access to
            // file no matter if it is a local or a remote one
            ContentProviderClient client = getContentResolver()
                    .acquireContentProviderClient(_path[0]);
            try {
                // Here we save copy of the file to temporary
                ParcelFileDescriptor descriptor = client.openFile(_path[0], "r");
                ParcelFileDescriptor.AutoCloseInputStream is = new ParcelFileDescriptor.AutoCloseInputStream(descriptor);
                file = File.createTempFile("image", ".jpg", getDir(null, MODE_PRIVATE));
                OutputStream outS = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = is.read(buf)) > 0) {
                    outS.write(buf, 0, len);
                }
                is.close();
                outS.close();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // And finally, we display the image
            String filePath = file.getAbsolutePath();
            return Drawable.createFromPath(filePath);
        }

        @Override
        protected void onPostExecute(Drawable _drawable) {
            imagePreview.setImageDrawable(_drawable);
            progressDialog.dismiss();
        }
    }
}