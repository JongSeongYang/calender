package com.himsoomzzin.calender.service;

import com.himsoomzzin.calender.domain.entity.UserEntity;
import com.himsoomzzin.calender.dto.Auth;
import com.himsoomzzin.calender.repository.UserRepository;
import com.himsoomzzin.calender.util.EncodingUtils;
import com.himsoomzzin.calender.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private static final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final JwtTokenProvider jwtTokenProvider;

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


    @Transactional
    public Long updateUser(Auth.UserRequest userRequest, UserEntity user) {
        if(userRequest.getLoginId() != null) user.setUserId(userRequest.getLoginId());
        if(userRequest.getPwd()!= null) user.setPwd(EncodingUtils.toPasswordHash(userRequest.getPwd()));
        if(userRequest.getUserName() != null) user.setUserName(userRequest.getUserName());
        if(userRequest.getNickName() != null) user.setNickName(userRequest.getNickName());
        if(userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if(userRequest.getPhoneNumber() != null) user.setPhoneNumber(userRequest.getPhoneNumber());
        if(userRequest.getImg() != null) user.setImg(userRequest.getImg());
        //if(request.getGroup() != null) user.set
        return saveUser(user);
    }

    @Transactional
    public Long update(Auth.UserRequest userRequest, HttpServletRequest request) {
        UserEntity userFromHeader = findUserFromHeader(request);
        if(userFromHeader == null){
            UserEntity user = new UserEntity();
            return updateUser(userRequest, user);
        } else return updateUser(userRequest, userFromHeader);
    }

    @Transactional
    public Long create(Auth.UserRequest userRequest) {
        UserEntity user = new UserEntity();
        return updateUser(userRequest, user);
    }

    public Map<String, String> getClaimMap(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("JWT");
        return jwtTokenProvider.getClaims(authorizationHeader);
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
}
