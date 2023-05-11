package com.zerobase.daangnmarketclone.domain.entity.user;

import com.zerobase.daangnmarketclone.domain.entity.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"userRegions"}, callSuper = true)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRegion> userRegions = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password, String nickname, String phoneNumber,
        String imageUrl, UserRole role, UserStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.role = role;
        this.userStatus = status;
    }

}
