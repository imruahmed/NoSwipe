package io.github.imruahmed.noswipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.File;
import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<PhotoItem> {

    private Context context;
    private int layoutResourceId;
    ArrayList<PhotoItem> allPhotoItems;

    public PhotoAdapter(Context context, int layoutResourceId, ArrayList<PhotoItem> items) {
        super(context, layoutResourceId);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.allPhotoItems = items;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);

    }

    public void setGridData(ArrayList<PhotoItem> items) {
        allPhotoItems.clear();
        for(int i=0; i<items.size(); i++) {
            allPhotoItems.add(items.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.setImageView((ImageView) convertView.findViewById(R.id.galleryItemImageView));
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhotoItem item = getItem(position);
        ImageLoader.getInstance().displayImage(item.getThumbnailPath(), viewHolder.getImageView());

        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageView;

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
