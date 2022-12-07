package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FollowVolDTO {
    @JsonProperty("Name")
    public String userName;

    @JsonProperty("PhoneNum")
    public long phoneNum;
}
