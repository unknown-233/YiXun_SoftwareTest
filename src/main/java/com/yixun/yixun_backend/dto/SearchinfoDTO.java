package com.yixun.yixun_backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SearchinfoDTO {
    @JsonProperty("search_info_id")
    public int search_info_id;
    @JsonProperty("search_type")
    public String search_type;
    @JsonProperty("sought_people_name")
    public String sought_people_name;
    @JsonProperty("search_info_lostdate")
    public String search_info_lostdate;
    @JsonProperty("sought_people_birthday")
    public String sought_people_birthday;
    @JsonProperty("province_id")
    public String province_id;
    @JsonProperty("city_id")
    public String city_id;
    @JsonProperty("area_id")
    public String area_id;
    @JsonProperty("detail")
    public String detail;
    @JsonProperty("search_info_photourl")
    public String search_info_photourl;
    @JsonProperty("contact_method")
    public String contact_method;
    @JsonProperty("sought_people_gender")
    public String sought_people_gender;


}
