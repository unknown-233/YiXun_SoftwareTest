package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClueDTO {
    @JsonProperty("ClueId")
    private int ClueId;
    @JsonProperty("ClueContent")
    private String ClueContent;
    @JsonProperty("ClueDate")
    private String ClueDate;
    @JsonProperty("SearchinfoId")
    private int SearchinfoId;
    @JsonProperty("WhetherConfirmed")
    private String WhetherConfirmed;
}