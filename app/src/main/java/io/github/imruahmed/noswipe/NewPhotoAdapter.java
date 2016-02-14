package io.github.imruahmed.noswipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class NewPhotoAdapter extends RecyclerView.Adapter<NewPhotoAdapter.ViewHolder> {

    private Context context;
    List<PhotoItem> photoItems;

    public NewPhotoAdapter(Context context, List<PhotoItem> photoItems) {
        this.context = context;
        this.photoItems = photoItems;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.gallery_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        View rowHolder = viewHolder.itemView;
        ImageView galleryItemImageView = (ImageView) rowHolder.findViewById(R.id.galleryItemImageView);
        PhotoItem item = photoItems.get(position);
        ImageLoader.getInstance().displayImage(item.getThumbnailPath(), galleryItemImageView);

    }

    @Override
    public int getItemCount() {
        if (photoItems == null) {
            return 0;
        }
        return photoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

