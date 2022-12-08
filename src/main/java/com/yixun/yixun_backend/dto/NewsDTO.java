package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewsDTO {
    @JsonProperty("NewsId")
    private int NewsId;

    @JsonProperty("NewsContent")
    private String NewsContent;

    @JsonProperty("Cover")
    private String Cover;

    @JsonProperty("Title")
    private String Title;
}
