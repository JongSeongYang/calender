package com.himsoomzzin.calender.controller;

import com.himsoomzzin.calender.dto.Auth;
import com.himsoomzzin.calender.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/sign-up")
    public ResponseEntity<Auth.UserResponse> signUp(@RequestBody Auth.UserRequest userRequest) {
        val response = Auth.UserResponse.builder()
                .message(userService.create(userRequest))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Auth.UserResponse> signIn(
            @RequestBody Auth.loginRequest userRequest) {
        val response = Auth.UserResponse.builder()
                .message(userService.verifyUser(userRequest, java.util.Optional.empty()))
                .build();
        return ResponseEntity.ok(response);
    }
}
