package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RelatedDpDTO {
    @JsonProperty("DpId")
    private int DpId;
    @JsonProperty("DpName")
    private String DpName;
    @JsonProperty("ContactMethod")
    private String ContactMethod;
    @JsonProperty("Province")
    private String Province;
    @JsonProperty("City")
    private String City;
    @JsonProperty("Area")
    private String Area;
    @JsonProperty("Detail")
    private String Detail;
}
