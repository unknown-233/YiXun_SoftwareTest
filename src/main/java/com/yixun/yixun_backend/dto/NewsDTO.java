package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewsDTO {
    @JsonProperty("NewsId")
    public int NewsId;

    @JsonProperty("NewsContent")
    public String NewsContent;

    @JsonProperty("Cover")
    public String Cover;

    @JsonProperty("Title")
    public String Title;
}
