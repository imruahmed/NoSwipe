package io.github.imruahmed.noswipe;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoItem implements Parcelable, Comparable<PhotoItem>{

    private String thumbnailPath;
    private String fullImagePath;
    private int order;

    public PhotoItem(String thumbnailPath, String fullImageUri, int order) {
        super();
        this.thumbnailPath = thumbnailPath;
        this.fullImagePath = fullImageUri;
        this.order = order;
    }

    public PhotoItem(Parcel source){

        thumbnailPath = source.readString();
        fullImagePath = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(thumbnailPath);
        parcel.writeString(fullImagePath);

    }

    @Override
    public int compareTo(PhotoItem photoItem) {
        return photoItem.getOrder()-order;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getFullImagePath() { return fullImagePath; }

    public void setFullImagePath(String fullImagePath) { this.fullImagePath = fullImagePath; }

    public int getOrder() { return order; }



    public static final Parcelable.Creator<PhotoItem> CREATOR = new Parcelable.Creator<PhotoItem>() {
        public PhotoItem createFromParcel(Parcel in) {
            return new PhotoItem(in);
        }

        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };


}
