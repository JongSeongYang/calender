package com.himsoomzzin.calender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Auth {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserRequest {
        private String loginId;
        private String pwd;
        private String email;
        private String phoneNumber;
        private String nickName;
        private String userName;
        private String img;
        private Long group;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class loginRequest {
        private String loginId;
        private String pwd;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse {
        private String message;
        private Long userId;
    }
}
