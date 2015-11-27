package io.github.imruahmed.noswipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;


public class GalleryPageFragment extends Fragment {

    public PhotoItem item;
    public Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_gallery_page, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.page_image_view);
        
        Picasso.with(context).load(new File(item.getFullImageUri().toString())).into(imageView);

        return rootView;
    }
}
