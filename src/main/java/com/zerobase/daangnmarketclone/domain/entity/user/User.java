package com.zerobase.daangnmarketclone.domain.entity.user;

import com.zerobase.daangnmarketclone.domain.entity.BaseTimeEntity;
import com.zerobase.daangnmarketclone.domain.entity.InterestCategory;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    @Embedded
    private Profile profile;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserRegion> userRegions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<InterestCategory> interestCategories = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password, Profile profile, String phoneNumber,
        UserRole role, UserStatus status, List<InterestCategory> interestCategories) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.userStatus = status;
        this.interestCategories =interestCategories;
    }

    public boolean hasRegion(Region region) {
        return userRegions.contains(region);
    }

    public void updateProfile(Profile profile) {
        this.profile.update(profile);
    }

    public UserRegion getRepresentRegion() {
        return userRegions.stream()
            .filter(UserRegion::isRepresent)
            .findFirst()
            .orElseThrow(() -> new CustomException(ErrorCode.USER_REGION_NOT_FOUND));
    }
}
