package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VolunteerDTO {
    @JsonProperty("VolunteerTime")
    private Integer VolTime;

    @JsonProperty("UserName")
    private String UserName;

    @JsonProperty("UserHeadUrl")
    private String UserHeadUrl;
}
