package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RelatedDpDTO {
    @JsonProperty("DpId")
    public int DpId;
    @JsonProperty("DpName")
    public String DpName;
    @JsonProperty("ContactMethod")
    public String ContactMethod;
    @JsonProperty("Province")
    public String Province;
    @JsonProperty("City")
    public String City;
    @JsonProperty("Area")
    public String Area;
    @JsonProperty("Detail")
    public String Detail;
}
