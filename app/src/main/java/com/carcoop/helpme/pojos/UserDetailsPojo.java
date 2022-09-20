package com.carcoop.helpme.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetailsPojo implements Parcelable {
    public static final Creator<UserDetailsPojo> CREATOR = new Creator<UserDetailsPojo>() {
        @Override
        public UserDetailsPojo createFromParcel(Parcel in) {
            return new UserDetailsPojo(in);
        }

        @Override
        public UserDetailsPojo[] newArray(int size) {
            return new UserDetailsPojo[size];
        }
    };
    private String Fullname;
    private String carRegNumber;
    private String carBrandName;
    private String carModel;

    public UserDetailsPojo() {
    }

    protected UserDetailsPojo(Parcel in) {
        Fullname = in.readString();
        carRegNumber = in.readString();
        carBrandName = in.readString();
        carModel = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Fullname);
        dest.writeString(carRegNumber);
        dest.writeString(carBrandName);
        dest.writeString(carModel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public void setCarRegNumber(String carRegNumber) {
        this.carRegNumber = carRegNumber;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
