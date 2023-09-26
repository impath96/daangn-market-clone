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
import lombok.Setter;

@Entity
@Table(name = "user_region")
@Getter
@Setter
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
    private boolean isAuthenticated;

    // 대표 동네 설정 상태
    private boolean isRepresent;

    public static UserRegion create(User user, Region region) {
        return UserRegion.builder()
            .isRepresent(true)
            .user(user)
            .region(region)
            .build();
    }

    public boolean isOwner(Long userId) {
        return user.getId().equals(userId);
    }

    public void authenticate() {
        this.isAuthenticated = true;
    }

    public void setRepresent(boolean isRepresent) {
        this.isRepresent = isRepresent;
    }
}
