package com.zerobase.daangnmarketclone.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponseDto<T> {

    private Meta meta;

    private List<T> documents = new ArrayList<>();

}
