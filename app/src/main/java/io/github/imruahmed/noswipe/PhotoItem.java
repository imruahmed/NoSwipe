package io.github.imruahmed.noswipe;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

public class PhotoItem implements Parcelable{
    private String image;
    private Uri thumbnailPath;
    private Uri fullImageUri;

    public PhotoItem(String image, Uri thumbnailPath, Uri fullImageUri) {
        super();
        this.image = image;
        this.thumbnailPath = thumbnailPath;
        this.fullImageUri = fullImageUri;
    }

    public PhotoItem(Parcel source){
        image = source.readString();
        thumbnailPath = Uri.parse(source.readString());
        fullImageUri = Uri.parse(source.readString());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(image);
        parcel.writeString(thumbnailPath.toString());
        parcel.writeString(fullImageUri.toString());

    }

    public static final Parcelable.Creator<PhotoItem> CREATOR = new Parcelable.Creator<PhotoItem>() {
        public PhotoItem createFromParcel(Parcel in) {
            return new PhotoItem(in);
        }

        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };
}
