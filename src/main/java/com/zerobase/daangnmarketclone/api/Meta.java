package com.zerobase.daangnmarketclone.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Meta {

    @JsonProperty(value = "total_count")
    private int totalCount;

}
