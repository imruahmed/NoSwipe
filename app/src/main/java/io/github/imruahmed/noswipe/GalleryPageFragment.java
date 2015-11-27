package io.github.imruahmed.noswipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;


public class GalleryPageFragment extends Fragment {

    public PhotoItem item;
    public Context context;

    private TouchImageView touchImageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_gallery_page, container, false);

        touchImageView = (TouchImageView) rootView.findViewById(R.id.pageImageView);

        Picasso.with(context).load(new File(item.getFullImagePath())).into(touchImageView);

        return rootView;
    }
}
