package io.github.imruahmed.noswipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<PhotoItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<PhotoItem> allPhotos = new ArrayList<PhotoItem>();

    public PhotoAdapter(Context context, int layoutResourceId, ArrayList allPhotos) {
        super(context, layoutResourceId, allPhotos);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.allPhotos = allPhotos;
    }

    public void setGridData(ArrayList<PhotoItem> allPhotos) {
        this.allPhotos = allPhotos;
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

        PhotoItem item = allPhotos.get(position);
        Picasso.with(context).load(new File(item.getThumbnailPath())).into(viewHolder.getImageView());

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
