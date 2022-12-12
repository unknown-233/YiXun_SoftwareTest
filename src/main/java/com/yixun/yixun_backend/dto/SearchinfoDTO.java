package com.yixun.yixun_backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SearchinfoDTO {
    @JsonProperty("search_info_id")
    private int search_info_id;
    @JsonProperty("search_type")
    private String search_type;
    @JsonProperty("sought_people_name")
    private String sought_people_name;
    @JsonProperty("search_info_lostdate")
    private String search_info_lostdate;
    @JsonProperty("sought_people_birthday")
    private String sought_people_birthday;
    @JsonProperty("province_id")
    private String province_id;
    @JsonProperty("city_id")
    private String city_id;
    @JsonProperty("area_id")
    private String area_id;
    @JsonProperty("detail")
    private String detail;
    @JsonProperty("search_info_photourl")
    private String search_info_photourl;
    @JsonProperty("contact_method")
    private String contact_method;
    @JsonProperty("sought_people_gender")
    private String sought_people_gender;

}
