package com.zerobase.daangnmarketclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegionDto {

    private String state;
    private String city;
    private String town;

    @Builder
    public RegionDto(String state, String city, String town) {
        this.state = state;
        this.city = city;
        this.town = town;
    }
}
