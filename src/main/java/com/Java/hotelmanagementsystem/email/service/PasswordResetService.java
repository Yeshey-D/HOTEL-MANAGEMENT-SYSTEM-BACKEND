package com.Java.hotelmanagementsystem.email.service;

import com.Java.hotelmanagementsystem.email.dto.ForgotPasswordRequest;
import com.Java.hotelmanagementsystem.email.dto.ResetPasswordRequest;
import lombok.NonNull;

public interface PasswordResetService {

    /**
     * Initializes the password reset process for the dedicated user.
     *
     * @param request The request containing the user email for whom the password reset link is to be generated.
     * @return The confirmation on the password creation link.
     */
    String forgotPassword(@NonNull ForgotPasswordRequest request);

    /**
     * Resets the password with the provided token and the password
     *
     * @param resetPasswordRequest The request object with password and the token.
     * @return The confirmation message for the reset password process.
     */
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
}