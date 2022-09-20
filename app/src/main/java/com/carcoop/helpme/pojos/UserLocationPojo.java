package com.carcoop.helpme.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLocationPojo implements Parcelable {

    public static final Creator<UserLocationPojo> CREATOR = new Creator<UserLocationPojo>() {
        @Override
        public UserLocationPojo createFromParcel(Parcel in) {
            return new UserLocationPojo(in);
        }

        @Override
        public UserLocationPojo[] newArray(int size) {
            return new UserLocationPojo[size];
        }
    };
    private double lat;
    private double lng;
    private String country;
    private String city;
    private String state;
    private String mnRoad;
    private String street;
    private String addressBaseline;

    public UserLocationPojo() {
    }

    protected UserLocationPojo(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
        country = in.readString();
        city = in.readString();
        state = in.readString();
        mnRoad = in.readString();
        street = in.readString();
        addressBaseline = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(mnRoad);
        dest.writeString(street);
        dest.writeString(addressBaseline);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMnRoad() {
        return mnRoad;
    }

    public void setMnRoad(String mnRoad) {
        this.mnRoad = mnRoad;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressBaseline() {
        return addressBaseline;
    }

    public void setAddressBaseline(String addressBaseline) {
        this.addressBaseline = addressBaseline;
    }
}
