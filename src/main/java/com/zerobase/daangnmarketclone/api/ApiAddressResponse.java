package com.zerobase.daangnmarketclone.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiAddressResponse {

    @JsonProperty(value = "address_name")
    private String addressName;

    @JsonProperty(value = "region_1depth_name")
    private String region1DepthName;

    @JsonProperty(value = "region_2depth_name")
    private String region2DepthName;

    @JsonProperty(value = "region_3depth_name")
    private String region3DepthName;



}
