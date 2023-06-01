package com.zerobase.daangnmarketclone.service;

import com.zerobase.daangnmarketclone.domain.entity.InterestCategory;
import com.zerobase.daangnmarketclone.domain.entity.user.User;
import com.zerobase.daangnmarketclone.domain.repository.InterestCategoryRepository;
import com.zerobase.daangnmarketclone.domain.repository.UserRepository;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestCategoryService {

    private final UserRepository userRepository;
    private final InterestCategoryRepository interestCategoryRepository;

    // 실제로는 카테고리를 추가 하는 것이 아니라 관심 있음 상태로 변경하는 것
    @Transactional
    public void add(String userEmail, Long interestCategoryId) {

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        InterestCategory interestCategory = interestCategoryRepository.findByIdAndUserId(
                interestCategoryId, user.getId())
            // 유저가 설정하지 않은 관심 카테고리
            .orElseThrow(() -> new CustomException(ErrorCode.UN_MATCHED_USER_INTEREST_CATEGORY));

        if (!interestCategory.isInterested()) {
            // 관심 없음 상태 아님 -> 이미 등록된 관심 카테고리
            throw new CustomException(ErrorCode.ALREADY_ADDED_INTEREST_CATEGORY);
        }

        interestCategory.setInterested(true);

    }

    // 실제로는 카테고리를 삭제하는 것이 아니라 관심 없음 상태로 변경하는 것
    @Transactional
    public void delete(String userEmail, Long interestCategoryId) {

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        InterestCategory interestCategory = interestCategoryRepository.findByIdAndUserId(
                interestCategoryId, user.getId())

            .orElseThrow(() -> new CustomException(ErrorCode.UN_MATCHED_USER_INTEREST_CATEGORY));

        if (interestCategory.isInterested()) {
            // 이미 관심 없음 상태 -> 이미 등록되어 있지 않은 관심 카테고리
            throw new CustomException(ErrorCode.ALREADY_NOT_ADDED_INTEREST_CATEGORY);
        }

        interestCategory.setInterested(false);

    }
}
