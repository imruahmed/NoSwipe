package io.github.imruahmed.noswipe;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoItem implements Parcelable{

    private String thumbnailPath;
    private String fullImagePath;

    public PhotoItem(String thumbnailPath, String fullImageUri) {
        super();
        this.thumbnailPath = thumbnailPath;
        this.fullImagePath = fullImageUri;
    }

    public PhotoItem(Parcel source){

        thumbnailPath = source.readString();
        fullImagePath = source.readString();
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getFullImagePath() { return fullImagePath; }

    public void setFullImagePath(String fullImagePath) { this.fullImagePath = fullImagePath; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(thumbnailPath);
        parcel.writeString(fullImagePath);

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
