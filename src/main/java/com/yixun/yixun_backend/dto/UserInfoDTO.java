package com.yixun.yixun_backend.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private String isactive;
    private String user_state;
    private int user_id;
    private String user_name;
    private String fundation_time;
    private long phone_num;
    private int search_info_num;//发布的寻人信息
    private int info_report_num;//举报的信息数
    private int clue_num;//发布的线索数
    private int clue_report_num;//举报的线索数

}
