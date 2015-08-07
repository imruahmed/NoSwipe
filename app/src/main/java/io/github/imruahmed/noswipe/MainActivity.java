package io.github.imruahmed.noswipe;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
    private final String orderBy = MediaStore.Images.Media._ID;

    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsSelection;
    private String[] arrPath;
    private ImageAdapter imageAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor imageCursor = managedQuery(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                columns, null, null, orderBy);

        int imageColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);

        this.count = imageCursor.getCount();
        this.thumbnails = new Bitmap[this.count];
        this.arrPath = new String[this.count];
        this.thumbnailsSelection = new boolean[this.count];

        for (int i = 0; i < this.count; i++) {
            imageCursor.moveToPosition(i);

            int id = imageCursor.getInt(imageColumnIndex);
            int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);

            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
                    getApplicationContext().getContentResolver(), id,
                    MediaStore.Images.Thumbnails.MICRO_KIND, null);

            arrPath[i]= imageCursor.getString(dataColumnIndex);
        }
        GridView imageGrid = (GridView) findViewById(R.id.image_grid);

        imageAdapter = new ImageAdapter();
        imageGrid.setAdapter(imageAdapter);

        imageCursor.close();

        Button selectBtn = (Button) findViewById(R.id.select_button);
        selectBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                int len = thumbnailsSelection.length;
                int cnt = 0;
                String selectImages = "";

                for (int i =0; i<len; i++) {
                    if (thumbnailsSelection[i]){
                        cnt++;
                        selectImages = selectImages + arrPath[i] + "|";
                    }
                }

                if (cnt == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Select an image", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Image(s) selected: " + cnt, Toast.LENGTH_SHORT).show();

                    Log.v("SelectedImages", selectImages);
                }
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
            int imageId;
        }

        public ImageAdapter() {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.grid_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.checkBox.setId(position);
            viewHolder.imageView.setId(position);

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();

                    if (thumbnailsSelection[id]) {
                        cb.setChecked(false);
                        thumbnailsSelection[id] = false;
                    } else {
                        cb.setChecked(true);
                        thumbnailsSelection[id] = true;
                    }
                }
            });

            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int id = v.getId();

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
                    startActivity(intent);
                }
            });

            viewHolder.imageView.setImageBitmap(thumbnails[position]);
            viewHolder.checkBox.setChecked(thumbnailsSelection[position]);
            viewHolder.imageId = position;

            return convertView;
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
    }
}