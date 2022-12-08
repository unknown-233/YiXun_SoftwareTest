package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FollowVolDTO {
    @JsonProperty("Name")
    private String userName;

    @JsonProperty("PhoneNum")
    private long phoneNum;
}
