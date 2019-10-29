package com.zona.recreativacr.com.zona.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Package implements Parcelable {
    public boolean active, breakfast, coffe, lunch;
    public String capacity, descrip, id, imgURL, name;
    public String price, refImage, refThumbnail, thumbnailURL;

    public Package() {
    }

    public Package(boolean breakfast, boolean coffe, boolean lunch, String capacity, String descrip, String id, String imgURL, String name, String price, String refImage, String refThumbnail, String thumbnailURL) {
        this.active = true;
        this.breakfast = breakfast;
        this.coffe = coffe;
        this.lunch = lunch;
        this.capacity = capacity;
        this.descrip = descrip;
        this.id = id;
        this.imgURL = imgURL;
        this.name = name;
        this.price = price;
        this.refImage = refImage;
        this.refThumbnail = refThumbnail;
        this.thumbnailURL = thumbnailURL;
    }

    public static final Parcelable.Creator<Package> CREATOR = new Parcelable.Creator<Package>() {
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(active ? 1 : 0);
        dest.writeInt(breakfast ? 1 : 0);
        dest.writeInt(coffe ? 1 : 0);
        dest.writeInt(lunch ? 1 : 0);
        dest.writeString(capacity);
        dest.writeString(descrip);
        dest.writeString(id);
        dest.writeString(imgURL);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(refImage);
        dest.writeString(refThumbnail);
        dest.writeString(thumbnailURL);
    }

    private Package(Parcel in ) {
        active = in.readInt() == 1;
        breakfast = in.readInt() == 1;
        coffe = in.readInt() == 1;
        lunch = in.readInt() == 1;
        capacity = in.readString();
        descrip = in.readString();
        id = in.readString();
        imgURL = in.readString();
        name = in.readString();
        price = in.readString();
        refImage = in.readString();
        refThumbnail = in.readString();
        thumbnailURL = in.readString();
    }
}
