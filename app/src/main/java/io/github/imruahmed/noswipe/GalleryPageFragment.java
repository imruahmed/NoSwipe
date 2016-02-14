package io.github.imruahmed.noswipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;


public class GalleryPageFragment extends Fragment {

    public PhotoItem item;
    public Context context;

    private TouchImageView touchImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_gallery_page, container, false);
        touchImageView = (TouchImageView) rootView.findViewById(R.id.pageImageView);

        ImageLoader.getInstance().displayImage(item.getFullImagePath(), touchImageView);

        return rootView;
    }
}
