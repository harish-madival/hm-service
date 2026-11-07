package com.hotel.auth.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "otp_verifications")
public class OtpVerification {
    @Id
    private String id;
    
    @Indexed
    private String mobileNumber;
    
    private String otp;
    private LocalDateTime expiryTime;
}