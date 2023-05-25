package com.zerobase.daangnmarketclone.domain.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_region")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_region_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    // 동네 인증 상태(유무)

    // 대표 동네 설정 상태
    private boolean isRepresent;

    //== 연관관계 매핑 편의 메서드 ==//
    public void addUser(User user) {
        this.user = user;
        user.getUserRegions().add(this);
    }

    public void addRegion(Region region) {
        this.region = region;
    }

    //== 생성 메서드 ==//
    public static UserRegion create(User user, Region region) {
        UserRegion userRegion = UserRegion.builder()
            .isRepresent(true)
            .build();
        userRegion.addUser(user);
        userRegion.addRegion(region);
        return userRegion;
    }

}
