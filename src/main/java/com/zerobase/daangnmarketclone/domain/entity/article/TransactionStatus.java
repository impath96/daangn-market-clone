package com.zerobase.daangnmarketclone.domain.entity.article;

public enum TransactionStatus {

    SELL("판매중"), BOOKED("예약 중"),SOLD_OUT("판매 완료");

    private String statusName;

    TransactionStatus(String statusName) {
        this.statusName = statusName;
    }
}
