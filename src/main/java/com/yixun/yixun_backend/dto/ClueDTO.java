package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClueDTO {
    @JsonProperty("ClueId")
    public int ClueId;
    @JsonProperty("ClueContent")
    public String ClueContent;
    @JsonProperty("ClueDate")
    public String ClueDate;
    @JsonProperty("SearchinfoId")
    public int SearchinfoId;
}