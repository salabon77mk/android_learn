package com.bignerdranch.android.photo_gallery;

import androidx.fragment.app.Fragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}
