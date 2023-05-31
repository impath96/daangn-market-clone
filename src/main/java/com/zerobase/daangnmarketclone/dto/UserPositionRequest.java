package com.zerobase.daangnmarketclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPositionRequest {

    // 현재 내 위치
    private Double longitude;   // 경도

    private Double latitude;    // 위도

}
