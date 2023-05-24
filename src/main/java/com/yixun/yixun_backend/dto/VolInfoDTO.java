package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VolInfoDTO {
    private String user_state;
    private int user_id;
    private String user_name;
    //private String fundation_time;
    private long phone_num;
    private String mail_num;
    private int vol_id;
    //private int vol_time;
    private int info_followup_num;
    private int act_num;
    private String province_id;
    private String city_id;
    private String area_id;
    private String detail;
}
