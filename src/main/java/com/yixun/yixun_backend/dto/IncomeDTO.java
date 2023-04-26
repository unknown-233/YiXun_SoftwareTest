package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class IncomeDTO {
    @JsonProperty("userHeadUrl")
    private String userHeadUrl;

    @JsonProperty("phoneNum")
    private String phoneNum;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("time")
    private String time;
}
