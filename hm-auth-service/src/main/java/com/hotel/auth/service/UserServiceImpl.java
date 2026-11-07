package com.hotel.auth.service;

import com.hotel.auth.config.ConfigProperties;
import com.hotel.auth.dao.UserRepository;
import com.hotel.auth.exception.ValidationException;
import com.hotel.auth.model.LogInDetails;
import com.hotel.auth.security.JwtTokenUtil;
import com.hotel.common.model.User;
import com.hotel.common.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OtpService otpService;

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public Optional<User> findUser(String mobileNumber) {
        return userRepository.findUser(mobileNumber);
    }

    @Override
    public User save(UserRequest userRequest) {
        if (userRequest == null || userRequest.getUserType() == null || userRequest.getMobileNumber() == null
                || !userRequest.getMobileNumber().matches("^\\d{10,15}$") || isBlank(userRequest.getUserName())
                || isBlank(userRequest.getPassword()) || !userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new ValidationException("Invalid user registration data", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setMobileNumber(userRequest.getMobileNumber());
        newUser.setCreatedBy(userRequest.getMobileNumber());
        if (userRequest.getUserType().name().equalsIgnoreCase("ADMIN")) {
            newUser.setRoles("ADMIN_ROLE");
        } else if (userRequest.getUserType().name().equalsIgnoreCase("PARTNER")) {
            newUser.setRoles("PARTNER_ROLE");
        } else {
            newUser.setRoles("ENTERPRISE_ROLE");
        }

        newUser.setUserType(userRequest.getUserType());
        newUser.setCreatedDate(Date.from(Instant.now()));
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setUserName(userRequest.getUserName());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setConfirmPassword(null);
        return userRepository.save(newUser);
    }

    @Override
    public Map<String, Object> sendOtp(LogInDetails loginDetails) {
        String mobileNumber = loginDetails == null ? null : loginDetails.getMobileNumber();
        isValidMobileNumber(mobileNumber);
        String otp = otpService.generateOtp();
        otpService.saveOtp(mobileNumber, otp);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP sent to your mobile number");
        if (configProperties.isOtpRequiredInConsole()) {
            response.put("otp", otp);
        }
        return response;
    }

    @Override
    public Map<String, Object> verifyLogin(LogInDetails loginDetails) {
        String mobileNumber = loginDetails == null ? null : loginDetails.getMobileNumber();
        String otp = loginDetails == null ? null : loginDetails.getOtp();
        isValidMobileNumber(mobileNumber);
        if (otp == null || !otpService.validateOtp(mobileNumber, otp)) {
            throw new ValidationException("Invalid or expired OTP", HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findUser(mobileNumber);
        if (user.isPresent()) {
            return tokenResponse(user.get());
        } else {
            throw new ValidationException("User not exist.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Map<String, Object> login(LogInDetails loginDetails) {
        String userName = loginDetails == null ? null : loginDetails.getUserName();
        String password = loginDetails == null ? null : loginDetails.getPassword();
        if (isBlank(userName) || isBlank(password)) {
            throw new ValidationException("Username and password are required", HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findUser(userName);
        if (!user.isPresent() || user.get().getPassword() == null
                || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new ValidationException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } else {
            return tokenResponse(user.get());
        }

    }

    private Map<String, Object> tokenResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtTokenUtil.generateToken(user.getMobileNumber()));
        response.put("mobileNumber", user.getMobileNumber());
        return response;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static void isValidMobileNumber(String mobileNumber) {
        if (mobileNumber == null || !mobileNumber.matches("^\\d{10,15}$")) {
            throw new ValidationException("Invalid Mobile Number", HttpStatus.BAD_REQUEST);
        }
    }

}
