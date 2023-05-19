package com.zerobase.daangnmarketclone.domain.entity.user;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Profile {

    private String nickname;

    private String imageUrl;

    public void update(Profile profile) {
        if (profile.getImageUrl() != null) {
            this.imageUrl = profile.getImageUrl();
        }
        this.nickname = profile.getNickname();
    }
}
