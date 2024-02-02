package com.cos.cercat.user.app;

import com.cos.cercat.global.entity.Image;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.global.util.JwtTokenUtil;
import com.cos.cercat.user.cache.LogoutTokenRepository;
import com.cos.cercat.user.cache.UserCacheRepository;
import com.cos.cercat.user.cache.TokenCacheRepository;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;
import com.cos.cercat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheRepository userCacheRepository;
    private final TokenCacheRepository tokenCacheRepository;
    private final LogoutTokenRepository logoutTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public UserDTO findByEmail(String email) {
        return userCacheRepository.getUser(email).orElseGet(() ->
                userRepository.findByEmail(email).map(UserDTO::fromEntity).orElseThrow(
                        () -> new CustomException(ErrorCode.USER_NOT_FOUND)));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateUser(Long userId, String nickName, Image image) {
        User user = getUser(userId);
        user.createUserInfo(nickName, image);
        refreshUserCache(user);
    }

    @Transactional
    public void logout(String accessToken, String email) {
        tokenCacheRepository.deleteRefreshToken(accessToken);

        long accessTokenExpirationMillis = jwtTokenUtil.getAccessTokenExpirationMillis(accessToken);
        log.info("accessTokenExpirationMillis - {}", accessTokenExpirationMillis);
        logoutTokenRepository.setLogout(email, accessToken, accessTokenExpirationMillis);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Boolean isLoginUser(String userEmail) {
        return logoutTokenRepository.isLoginUser(userEmail);
    }

    public void refreshUserCache(User user) {
        userCacheRepository.deleteUser(user.getEmail());
        userCacheRepository.setUser(UserDTO.fromEntity(user));
    }
}
