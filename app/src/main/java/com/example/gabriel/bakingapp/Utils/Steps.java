package com.example.gabriel.bakingapp.Utils;


import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable{
    private int id;
    private String sDesc, desc, videoURL, thumbnailURL;

    public Steps(int i, String s, String d, String v, String t){
        id = i;
        sDesc = s;
        desc = d;
        videoURL = v;
        thumbnailURL = t;
    }

    public Steps(Parcel in) {
        id = in.readInt();
        sDesc = in.readString();
        desc = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public int getId(){return id;}
    public String getsDesc(){return sDesc;}
    public String getDesc(){return desc;}
    public String getVideoURL(){return videoURL;}
    public String getThumbnailURL(){return thumbnailURL;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(sDesc);
        dest.writeString(desc);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
