package com.example.majid_fit5.mornitask.data.models.blog;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public class Blog implements Parcelable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("small_description")
    @Expose
    private String smallDesc;

    @SerializedName("published_at")
    @Expose
    private String publishDate;

    @SerializedName("cover_image")
    @Expose
    private String coverImage;

    @SerializedName("body")
    @Expose
    public String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmallDesc() {
        return smallDesc;
    }

    public void setSmallDesc(String smallDesc) {
        this.smallDesc = smallDesc;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public Blog(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.smallDesc);
        dest.writeString(this.publishDate);
        dest.writeString(this.coverImage);
        dest.writeString(this.body);
    }

    public Blog(Parcel in){
        this.id=in.readInt();
        this.title = in.readString();
        this.smallDesc = in.readString();
        this.publishDate = in.readString();
        this.coverImage = in.readString();
        this.body = in.readString();
    }

    public static final Parcelable.Creator<Blog> CREATOR= new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel source) {
           return new Blog(source);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };
}
