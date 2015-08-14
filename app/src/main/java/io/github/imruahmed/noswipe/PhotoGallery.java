package io.github.imruahmed.noswipe;


import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhotoGallery extends Fragment {

    private AbsListView galleryListView;
    private PhotoAdapter photoAdapter;
    private ArrayList<PhotoItem> photoList;
    private ProgressDialog loadingProgressDialog;
    private Context context;

    public PhotoGallery() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an empty loader and pre-initialize the photo list items as an empty list.
        context = getActivity().getBaseContext();


        photoList = new ArrayList<PhotoItem>() ;
        photoAdapter = new PhotoAdapter(getActivity(), R.layout.photo_item, photoList, false);

        getLoaderManager().initLoader(R.id.loader_id, null, loaderCallbacks);

    }

    private LoaderManager.LoaderCallbacks<List<PhotoItem>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<PhotoItem>>() {
        @Override
        public Loader<List<PhotoItem>> onCreateLoader(int id, Bundle args) {
            return new PhotoGalleryAsyncLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<PhotoItem>> loader, List<PhotoItem> data) {
            photoList.clear();

            for(int i = 0; i < data.size(); i++){
                PhotoItem item = data.get(i);
                photoList.add(item);
            }

            photoAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<PhotoItem>> loader) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        // Set the mAdapter
        galleryListView = (AbsListView) view.findViewById(R.id.grid_list_view);
        galleryListView.setAdapter(photoAdapter);

        return view;

    }
}
