package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VolApplyInfoDTO {
    @JsonProperty("vol_apply_id")
    private int vol_apply_id;
    @JsonProperty("user_state")
    private String user_state;
    @JsonProperty("user_id")
    private int user_id;
    @JsonProperty("user_name")
    private String user_name;
    @JsonProperty("career")
    private String career;
    @JsonProperty("specialty")
    private String specialty;
    @JsonProperty("background")
    private String background;
    @JsonProperty("application_time")
    private String application_time;
    @JsonProperty("ispass")
    private String ispass;
}
