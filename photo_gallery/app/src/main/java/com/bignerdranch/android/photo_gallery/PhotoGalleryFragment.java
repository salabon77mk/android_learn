package com.bignerdranch.android.photo_gallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;


public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PHOTOGALLERYFRAGMENT";

    private RecyclerView mPhotoRecyclerView;

    public static PhotoGalleryFragment newInstance(){
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setRetainInstance(true);
        new FetchItemsTask().doInBackground();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return v;
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,Void>{
        final String nerdRanchURL = "https://www.bignerdranch.com";

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String result = new FlickrFetchr().getUrlString(nerdRanchURL);
                Log.i(TAG, "Fetched contents of URL: " + result);
            }
            catch (IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }
}
