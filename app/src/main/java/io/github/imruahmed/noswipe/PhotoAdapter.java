package io.github.imruahmed.noswipe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;


import java.util.List;


public class PhotoAdapter extends ArrayAdapter<PhotoItem>{

    // Ivars.
    private Context context;
    private int resourceId;

    public PhotoAdapter(Context context, int resourceId, List<PhotoItem> items, boolean useList) {
        super(context, resourceId, items);
        this.context = context;
        this.resourceId = resourceId;
    }

    private class ViewHolder {
        ImageView photoImageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        PhotoItem photoItem = getItem(position);
        //View view;

        // This block exists to inflate the photo list item conditionally based on whether
        // we want to support a grid or list view.
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//
//        if (convertView == null) {
//            holder = new ViewHolder();
//            view = inflater.inflate(resourceId, null);
//            holder.photoImageView = (ImageView) view.findViewById(R.id.imageView);
//            view.setTag(holder);
//        } else {
//            view = convertView;
//            holder = (ViewHolder) view.getTag();
//        }
//
//        // Set the thumbnail
//        holder.photoImageView.setImageURI(photoItem.getThumbnailUri());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.photo_item, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.windows);

        return view;
    }
}
