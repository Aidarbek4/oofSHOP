package com.example.offshop.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable, Parcelable {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("owner")
    @Expose
    int owner;

    @SerializedName("product")
    @Expose
    int product;

    @SerializedName("quantity")
    @Expose
    int quantity;

    @SerializedName("price")
    @Expose
    double price;

    public Order(int id, int owner, int product, int quantity, double price) {
        this.id = id;
        this.owner = owner;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    protected Order(Parcel in) {
        id = in.readInt();
        owner = in.readInt();
        product = in.readInt();
        quantity = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", owner=" + owner +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getOwner() {
        return owner;
    }

    public int getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(owner);
        dest.writeInt(product);
        dest.writeInt(quantity);
        dest.writeDouble(price);
    }
}
