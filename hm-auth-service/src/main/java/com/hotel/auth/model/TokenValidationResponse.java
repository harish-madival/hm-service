package com.hotel.auth.model;

/** Safe identity data returned after a JWT has been validated. */
public class TokenValidationResponse {

    private final boolean valid;
    private final String userId;
    private final String mobileNumber;
    private final String role;
    private final String userType;

    private TokenValidationResponse(boolean valid, String userId, String mobileNumber, String role, String userType) {
        this.valid = valid;
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.userType = userType;
    }

    public static TokenValidationResponse valid(String userId, String mobileNumber, String role, String userType) {
        return new TokenValidationResponse(true, userId, mobileNumber, role, userType);
    }

    public static TokenValidationResponse invalid() {
        return new TokenValidationResponse(false, null, null, null, null);
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
