package com.example.offshop.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable, Parcelable {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("user")
    @Expose
    int user;

    @SerializedName("text")
    @Expose
    String text;

    public Review(int id, int user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }

    protected Review(Parcel in) {
        id = in.readInt();
        user = in.readInt();
        text = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user);
        dest.writeString(text);
    }
}
