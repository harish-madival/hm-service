package com.harish.gateway.dto;

/** Safe identity data returned after a JWT has been validated. */
public class TokenValidationResponse {

    private boolean valid;
    private String userId;
    private String mobileNumber;
    private String role;
    private String userType;

    private TokenValidationResponse() {

    }

    private TokenValidationResponse(boolean valid, String userId, String mobileNumber, String role, String userType) {
        this.valid = valid;
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.userType = userType;
    }

    public boolean isValid() {
        return valid;
    }

    public String getUserId() {
        return userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getRole() {
        return role;
    }

    public String getUserType() {
        return userType;
    }
}
