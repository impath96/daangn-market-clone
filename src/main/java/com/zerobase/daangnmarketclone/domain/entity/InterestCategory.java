package com.zerobase.daangnmarketclone.domain.entity;

import com.zerobase.daangnmarketclone.domain.entity.user.User;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interest_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestCategory extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 유저 관심 설정 유무
    private boolean isInterested;

    @Builder
    public InterestCategory(Long id, User user, Category category, boolean isInterested) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.isInterested = isInterested;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInterested(boolean isInterested) {
        this.isInterested = isInterested;
    }
}
