package com.acker.busticketbackend.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.bytebuddy.utility.RandomString;

import org.springframework.stereotype.Service;

@Service
public class OTPService {
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    public String generateOTP(String email) {
        String otp = RandomString.make(6); 
        otpStore.put(email, otp);
        return otp;
    }

    public boolean validateOTP(String email, String otp) {
        String storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStore.remove(email);
            return true;
        }
        return false;
    }
}
