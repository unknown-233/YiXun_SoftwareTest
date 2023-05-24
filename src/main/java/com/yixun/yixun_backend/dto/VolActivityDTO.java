package com.yixun.yixun_backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VolActivityDTO {
    @JsonProperty("VolActId")
    private int VolActId;//biaohao
    @JsonProperty("VolActName")
    private String VolActName;//mingzi
    @JsonProperty("ExpTime")
    private String ExpTime;//time
    @JsonProperty("EndTime")
    private String EndTime;//time
    @JsonProperty("Detail")
    private String Detail;
    @JsonProperty("Province")
    private String Province;
    @JsonProperty("City")
    private String City;
    @JsonProperty("Area")
    private String Area;
    @JsonProperty("Needpeople")
    private int Needpeople;
    @JsonProperty("ActPicUrl")
    private String ActPicUrl;
    @JsonProperty("ContactMethod")
    private String ContactMethod;
    @JsonProperty("SignupPeople")
    private int SignupPeople;
    @JsonProperty("ReleaseTime")
    private String ReleaseTime;
}
