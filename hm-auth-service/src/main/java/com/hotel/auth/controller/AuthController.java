package com.hotel.auth.controller;

import com.hotel.auth.model.LogInDetails;
import com.hotel.auth.service.UserService;
import com.hotel.common.model.HmResponse;
import com.hotel.common.model.User;
import com.hotel.common.model.UserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/send-otp")
    public ResponseEntity<HmResponse> sendOtp(@RequestBody LogInDetails loginDetails) {
        return ResponseEntity.ok(new HmResponse("success", userService.sendOtp(loginDetails)));
    }

    @PostMapping("/verify-login")
    public ResponseEntity<HmResponse> verifyLogin(@RequestBody LogInDetails loginDetails) {
        return ResponseEntity.ok(new HmResponse("success", userService.verifyLogin(loginDetails)));
    }

    @PostMapping("/login")
    public ResponseEntity<HmResponse> login(@RequestBody LogInDetails loginDetails) {
        return ResponseEntity.ok(new HmResponse("success", userService.login(loginDetails)));
    }

    @PostMapping("/user")
    public ResponseEntity<HmResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        User user = userService.save(userRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("mobileNumber", user.getMobileNumber());
        response.put("userName", user.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new HmResponse("success", response));
    }

}
