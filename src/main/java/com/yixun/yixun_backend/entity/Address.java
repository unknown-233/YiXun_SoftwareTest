package com.yixun.yixun_backend.entity;

public class Address {
    private int ADDRESS_ID;
    private String AREA_ID;
    private String DETAIL;
    private String CITY_ID;
    private String PROVINCE_ID;

    public int getADDRESS_ID() {
        return ADDRESS_ID;
    }

    public void setADDRESS_ID(int ADDRESS_ID) {
        this.ADDRESS_ID = ADDRESS_ID;
    }

    public String getAREA_ID() {
        return AREA_ID;
    }

    public void setAREA_ID(String AREA_ID) {
        this.AREA_ID = AREA_ID;
    }

    public String getDETAIL() {
        return DETAIL;
    }

    public void setDETAIL(String DETAIL) {
        this.DETAIL = DETAIL;
    }

    public String getCITY_ID() {
        return CITY_ID;
    }

    public void setCITY_ID(String CITY_ID) {
        this.CITY_ID = CITY_ID;
    }

    public String getPROVINCE_ID() {
        return PROVINCE_ID;
    }

    public void setPROVINCE_ID(String PROVINCE_ID) {
        this.PROVINCE_ID = PROVINCE_ID;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ADDRESS_ID=" + ADDRESS_ID +
                ", AREA_ID='" + AREA_ID + '\'' +
                ", DETAIL='" + DETAIL + '\'' +
                ", CITY_ID='" + CITY_ID + '\'' +
                ", PROVINCE_ID='" + PROVINCE_ID + '\'' +
                '}';
    }
}
