package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ClueRepoInfoDTO {
    @JsonProperty("clue_repo_id")
    private int clue_repo_id;
    @JsonProperty("user_id")
    private int user_id;
    @JsonProperty("clue_id")
    private int clue_id;
    @JsonProperty("repo_content")
    private String repo_content;

    @JsonProperty("repo_time")
    private String repo_time;
    @JsonProperty("ispass")
    private String ispass;
}
