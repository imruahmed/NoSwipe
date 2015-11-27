package io.github.imruahmed.noswipe;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<PhotoItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<PhotoItem> photoList = new ArrayList<PhotoItem>();

    public PhotoAdapter(Context context, int layoutResourceId, ArrayList photoList) {
        super(context, layoutResourceId, photoList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.photoList = photoList;
    }

    public void setGridData(ArrayList<PhotoItem> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        PhotoItem item = photoList.get(position);
        Picasso.with(context).load(new File(item.getImage())).into(holder.image);

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
    }
}
