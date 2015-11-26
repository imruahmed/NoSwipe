package io.github.imruahmed.noswipe;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class DetailsActivity extends ActionBarActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = getBaseContext();

        String image_path = getIntent().getStringExtra("IMAGE_PATH");
        ImageView imageView = (ImageView) findViewById(R.id.image);

        Picasso.with(context).load(new File(image_path)).into(imageView);


    }
}
