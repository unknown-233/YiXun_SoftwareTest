package com.yixun.yixun_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClueRepoCountDTO
{
    @JsonProperty("clue_repo_notreviewed")
    private int clue_repo_notreviewed;
    @JsonProperty("clue_repo_reviewed")
    private int clue_repo_reviewed;
}