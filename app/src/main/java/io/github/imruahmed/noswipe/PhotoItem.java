package io.github.imruahmed.noswipe;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public class PhotoItem {

    private Uri thumbnailUri;
    private Uri fullImageUri;



    public PhotoItem(Uri thumbnailUri, Uri fullImageUri) {
        this.thumbnailUri = thumbnailUri;
        this.fullImageUri = fullImageUri;
    }

    public PhotoItem(){};

    public Uri getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(Uri thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public Uri getFullImageUri() {
        return fullImageUri;
    }

    public void setFullImageUri(Uri fullImageUri) {
        this.fullImageUri = fullImageUri;
    }

}