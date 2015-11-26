package io.github.imruahmed.noswipe;

import android.graphics.Bitmap;
import android.net.Uri;

public class PhotoItem {
    private String image;
    private Uri thumbnailPath;
    private Uri fullImageUri;

    public PhotoItem(String image, Uri thumbnailPath, Uri fullImageUri) {
        super();
        this.image = image;
        this.thumbnailPath = thumbnailPath;
        this.fullImageUri = fullImageUri;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Uri getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(Uri thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Uri getFullImageUri() { return fullImageUri; }

    public void setFullImageUri(Uri fullImageUri) { this.fullImageUri = fullImageUri; }
}
