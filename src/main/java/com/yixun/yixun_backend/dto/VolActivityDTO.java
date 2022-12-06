package com.yixun.yixun_backend.dto;
import lombok.Data;

@Data
public class VolActivityDTO {
    public int vol_act_id;
    public String vol_act_name;
    public String vol_act_content;
    public String vol_act_time;
    public String province_id;
    public String city_id;
    public String area_id;
    public String detail;
    public Integer need_people;
    public String actpicurl;
    public String contact_method;
}
