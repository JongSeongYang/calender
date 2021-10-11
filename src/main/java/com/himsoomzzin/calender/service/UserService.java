package com.himsoomzzin.calender.service;

import com.himsoomzzin.calender.controller.AuthController;
import com.himsoomzzin.calender.domain.entity.UserEntity;
import com.himsoomzzin.calender.dto.Auth;
import com.himsoomzzin.calender.repository.UserRepository;
import com.himsoomzzin.calender.util.EncodingUtils;
import com.himsoomzzin.calender.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private static final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Transactional
    public UserEntity findUser(Long id) {
        return userRepository.findUserEntityById(id);
    }

    @Transactional
    public Long saveUser(UserEntity user) {
        UserEntity save = userRepository.save(user);
        return save.getId();
    }

    @Transactional
    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    public UserEntity checkPhoneNum(String phoneNumber) {
        Optional<UserEntity> user = userRepository.findUserEntityByPhoneNumber(phoneNumber);
        return user.orElse(null);
    }

    @Transactional
    public String updateUser(Auth.UserRequest userRequest, UserEntity user) {
        if(checkLoginId(userRequest.getLoginId()) !=null) return "이미 존재하는 아이디입니다.";
        if(checkPhoneNum(userRequest.getPhoneNumber())!=null) return "이미 존재하는 회원입니다.";
        if(userRequest.getLoginId() != null) user.setUserId(userRequest.getLoginId());
        if(userRequest.getPwd()!= null) user.setPwd(EncodingUtils.toPasswordHash(userRequest.getPwd()));
        if(userRequest.getUserName() != null) user.setUserName(userRequest.getUserName());
        if(userRequest.getNickName() != null) user.setNickName(userRequest.getNickName());
        if(userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if(userRequest.getPhoneNumber() != null) user.setPhoneNumber(userRequest.getPhoneNumber());
        if(userRequest.getImg() != null) user.setImg(userRequest.getImg());
        //if(request.getGroup() != null) user.set
        saveUser(user);
        return "회원가입 성공";
    }

    @Transactional
    public String update(Auth.UserRequest userRequest, HttpServletRequest request) {
        UserEntity userFromHeader = findUserFromHeader(request);
        if(userFromHeader == null){
            UserEntity user = new UserEntity();
            return updateUser(userRequest, user);
        } else return updateUser(userRequest, userFromHeader);
    }

    @Transactional
    public String create(Auth.UserRequest userRequest) {
        UserEntity user = new UserEntity();
        return updateUser(userRequest, user);
    }

    public Map<String, String> getClaimMap(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("JWT");
        return jwtTokenProvider.getClaims(authorizationHeader);
    }

    public String getUserPwdFromToken(Map<String, String> claims) {
        return claims.get("pwd");
    }

    public String getUserLoginIdFromToken(Map<String, String> claims) {
        return claims.get("loginId");
    }

    public Long getUserId(Map<String, String> claims) {
        String userId = claims.get("userId");
        if(userId == null) return null;
        else return Long.parseLong(userId);
    }

    @Transactional
    public UserEntity findUserFromHeader(HttpServletRequest request) {
        Map<String, String> claims = getClaimMap(request);
        Long userId = getUserId(claims);
        if(userId == null) return null;
        else return findUser(userId);
    }

    public UserEntity checkLoginId(String loginId) {
        Optional<UserEntity> user = userRepository.findUserEntityByUserId(loginId);
        return user.orElse(null);
    }

    public boolean checkPwd(String pwd, UserEntity user) {
        return user.getPwd().equalsIgnoreCase(pwd);
    }

    @Transactional
    public String verifyUser(Auth.loginRequest userRequest, Optional<HttpServletRequest> request ){
        String loginId = "";
        String pwd = "";
        UserEntity user = null;
        logger.info("verifyUser");
        if(request.isPresent()){
            if(!jwtTokenProvider.checkExpToken(request.get().getHeader("JWT"))) return "token 이 만료되었습니다.";
            Map<String, String> claimMap = getClaimMap(request.get());
            loginId = getUserLoginIdFromToken(claimMap);
            pwd = getUserPwdFromToken(claimMap);
        }
        else{
            loginId = userRequest.getLoginId();
            pwd = EncodingUtils.toPasswordHash(userRequest.getPwd());
        }
        user = checkLoginId(loginId);
        if(user==null) return "잘못된 ID입니다.";
        if(!checkPwd(pwd, user)) return "잘못된 Password 입니다.";

        return jwtTokenProvider.createUserToken(loginId, pwd);
    }
}
