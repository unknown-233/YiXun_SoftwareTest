package com.yixun.yixun_backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VolActivityDTO {
    @JsonProperty("VolActId")
    public int VolActId;//biaohao
    @JsonProperty("VolActName")
    public String VolActName;//mingzi
    @JsonProperty("ExpTime")
    public String ExpTime;//time
    @JsonProperty("Detail")
    public String Detail;
    @JsonProperty("Province")
    public String Province;
    @JsonProperty("City")
    public String City;
    @JsonProperty("Area")
    public String Area;
    @JsonProperty("Needpeople")
    public int Needpeople;
    @JsonProperty("ActPicUrl")
    public String ActPicUrl;
    @JsonProperty("ContactMethod")
    public String ContactMethod;
    @JsonProperty("SignupPeople")
    public int SignupPeople;
}
