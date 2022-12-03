package com.yixun.yixun_backend.entity;

public class Address {
    private int addressID;
    private String areaID;
    private String detail;
    private String cityID;
    private String provinceID;

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(String provinceID) {
        this.provinceID = provinceID;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressID=" + addressID +
                ", areaID='" + areaID + '\'' +
                ", detail='" + detail + '\'' +
                ", cityID='" + cityID + '\'' +
                ", provinceID='" + provinceID + '\'' +
                '}';
    }
}
