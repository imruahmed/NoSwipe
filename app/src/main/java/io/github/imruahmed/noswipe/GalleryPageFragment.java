package io.github.imruahmed.noswipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class GalleryPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_gallery_page, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.page_image_view);
        imageView.setImageResource(R.drawable.windows);

        return rootView;
    }
}