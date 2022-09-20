package com.carcoop.helpme.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Emergency_detailPOJO implements Parcelable {

    public static final Creator<Emergency_detailPOJO> CREATOR = new Creator<Emergency_detailPOJO>() {
        @Override
        public Emergency_detailPOJO createFromParcel(Parcel in) {
            return new Emergency_detailPOJO(in);
        }

        @Override
        public Emergency_detailPOJO[] newArray(int size) {
            return new Emergency_detailPOJO[size];
        }
    };
    private String name;
    private String vehicleRegno;
    private String carBrand;
    private String carmodel;
    private String location;
    private String TimeandDatestamp;
    private String otherdrivername;
    private String otherdriverlicence;
    private String otherdrivateVehicleRegno;
    private String otherdrivercarBrand;
    private String otherdrivercarmodel;
    private String otherdriverMobilNumber;
    private String otherdriverHomeAddress;
    private String insurance;
    private ArrayList<String> imageuris;
    private ArrayList<String> videouris;

    public Emergency_detailPOJO() {
    }

    protected Emergency_detailPOJO(Parcel in) {
        name = in.readString();
        vehicleRegno = in.readString();
        carBrand = in.readString();
        carmodel = in.readString();
        location = in.readString();
        TimeandDatestamp = in.readString();
        otherdrivername = in.readString();
        otherdriverlicence = in.readString();
        otherdrivateVehicleRegno = in.readString();
        otherdrivercarBrand = in.readString();
        otherdrivercarmodel = in.readString();
        otherdriverMobilNumber = in.readString();
        otherdriverHomeAddress = in.readString();
        insurance = in.readString();
        imageuris = in.createStringArrayList();
        videouris = in.createStringArrayList();
    }

    public static Creator<Emergency_detailPOJO> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(vehicleRegno);
        dest.writeString(carBrand);
        dest.writeString(carmodel);
        dest.writeString(location);
        dest.writeString(TimeandDatestamp);
        dest.writeString(otherdrivername);
        dest.writeString(otherdriverlicence);
        dest.writeString(otherdrivateVehicleRegno);
        dest.writeString(otherdrivercarBrand);
        dest.writeString(otherdrivercarmodel);
        dest.writeString(otherdriverMobilNumber);
        dest.writeString(otherdriverHomeAddress);
        dest.writeString(insurance);
        dest.writeStringList(imageuris);
        dest.writeStringList(videouris);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getOtherdrivateVehicleRegno() {
        return otherdrivateVehicleRegno;
    }

    public void setOtherdrivateVehicleRegno(String otherdrivateVehicleRegno) {
        this.otherdrivateVehicleRegno = otherdrivateVehicleRegno;
    }

    public String getOtherdriverMobilNumber() {
        return otherdriverMobilNumber;
    }

    public void setOtherdriverMobilNumber(String otherdriverMobilNumber) {
        this.otherdriverMobilNumber = otherdriverMobilNumber;
    }

    public String getOtherdriverHomeAddress() {
        return otherdriverHomeAddress;
    }

    public void setOtherdriverHomeAddress(String otherdriverHomeAddress) {
        this.otherdriverHomeAddress = otherdriverHomeAddress;
    }

    public String getOtherdrivername() {
        return otherdrivername;
    }

    public void setOtherdrivername(String otherdrivername) {
        this.otherdrivername = otherdrivername;
    }

    public String getOtherdriverlicence() {
        return otherdriverlicence;
    }

    public void setOtherdriverlicence(String otherdriverlicence) {
        this.otherdriverlicence = otherdriverlicence;
    }

    public String getOtherdrivercarBrand() {
        return otherdrivercarBrand;
    }

    public void setOtherdrivercarBrand(String otherdrivercarBrand) {
        this.otherdrivercarBrand = otherdrivercarBrand;
    }

    public String getOtherdrivercarmodel() {
        return otherdrivercarmodel;
    }

    public void setOtherdrivercarmodel(String otherdrivercarmodel) {
        this.otherdrivercarmodel = otherdrivercarmodel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleRegno() {
        return vehicleRegno;
    }

    public void setVehicleRegno(String vehicleRegno) {
        this.vehicleRegno = vehicleRegno;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getImageuris() {
        return imageuris;
    }

    public void setImageuris(ArrayList<String> imageuris) {
        this.imageuris = imageuris;
    }

    public ArrayList<String> getVideouris() {
        return videouris;
    }

    public void setVideouris(ArrayList<String> videouris) {
        this.videouris = videouris;
    }

    public String getTimeandDatestamp() {
        return TimeandDatestamp;
    }

    public void setTimeandDatestamp(String timeandDatestamp) {
        TimeandDatestamp = timeandDatestamp;
    }
}
