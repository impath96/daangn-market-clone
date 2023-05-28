package com.zerobase.daangnmarketclone.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiTotalAddressResponse {

    @JsonProperty(value = "road_address")
    private ApiRoadAddressResponse roadAddress;

    private ApiAddressResponse address;

}
