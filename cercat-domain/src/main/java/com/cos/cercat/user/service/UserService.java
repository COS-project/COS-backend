package com.cos.cercat.user.service;

import com.cos.cercat.common.domain.Image;
import com.cos.cercat.exception.CustomException;
import com.cos.cercat.exception.ErrorCode;
import com.cos.cercat.user.cache.*;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.repository.UserRepository;
import com.nimbusds.jose.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    private final TokenCacheRepository tokenCacheRepository;
    private final LogoutTokenRepository logoutTokenRepository;
    private final JwtTokenService jwtTokenService;

    @Cacheable(cacheNames = "USER", key = "#email")
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::fromEntity)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateUser(Long userId, String nickName, Image image) {
        User user = getUser(userId);
        user.createUserInfo(nickName, image);
        refreshUserCache(user);
        log.info("user - {} 프로필 수정", user.getEmail());
    }

    public void logout(String accessToken, String email) {
        tokenCacheRepository.deleteRefreshToken(accessToken);

        long accessTokenExpirationMillis = jwtTokenService.getAccessTokenExpirationMillis(accessToken);
        log.info("accessTokenExpirationMillis - {}", accessTokenExpirationMillis);
        logoutTokenRepository.setLogout(email, accessToken, accessTokenExpirationMillis);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Boolean isLoginUser(Pair<String, String> tokenAndEmail) {
        return logoutTokenRepository.isLoginUser(tokenAndEmail);
    }

    public void refreshUserCache(User user) {
        log.info("user - {} 유저 캐시 리프레시", user.getEmail());
        userCacheRepository.deleteUser(user.getEmail());
        userCacheRepository.setUser(UserDTO.fromEntity(user));
    }
}