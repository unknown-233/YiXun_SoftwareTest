package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;


@Data
public class NewsManageDTO {
    @JsonProperty("news_id")
    private int news_id;
    @JsonProperty("administrator_id")
    private int administrator_id;
    @JsonProperty("news_type")
    private String news_type;

    @JsonProperty("Isactive")
    private String Isactive;
    @JsonProperty("news_time")
    private String news_time;

    @JsonProperty("news_title")
    private String news_title;

}