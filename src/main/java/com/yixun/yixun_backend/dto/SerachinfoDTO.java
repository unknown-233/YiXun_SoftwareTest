package com.yixun.yixun_backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SerachinfoDTO{

    /**
     * 被寻找人姓名
     */
    @JsonProperty("SoughtPeopleName")
    private String SoughtPeopleName;

    /**
     * 被寻找人年龄
     */
    @JsonProperty("SoughtPeopleBirthday")
    private String SoughtPeopleBirthday;

    /**
     * 被寻找人性别
     */
    @JsonProperty("SoughtPeopleGender")
    private String SoughtPeopleGender;

    /**
     * 寻人类型
     */
    @JsonProperty("SearchType")
    private String SearchType;

    /**
     * 走失日期
     */
    @JsonProperty("SearchinfoLostdate")
    private String SearchinfoLostdate;


    /**
     * 被寻找人照片
     */
    @JsonProperty("SearchinfoPhotoURL")
    private String SearchinfoPhotoURL;

    /**
     * 失散地点—省
     */
    @JsonProperty("Province")
    private String Province;

    /**
     * 失散地点-市
     */
    @JsonProperty("City")
    private String City;

    /**
     *  失散地点-区
     */
    @JsonProperty("Area")
    private String Area;

    /**
     *  失散地点-详细地址
     */
    @JsonProperty("Detail")
    private String Detail;

}
