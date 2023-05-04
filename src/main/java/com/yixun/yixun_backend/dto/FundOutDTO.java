package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FundOutDTO {
    @JsonProperty("fund_out_id")
    private int fund_out_id;

    @JsonProperty("fund_out_amount")
    private double fund_out_amount;

    @JsonProperty("fund_out_time")
    private String fund_out_time;

    @JsonProperty("fund_out_usage")
    private String fund_out_usage;

}
