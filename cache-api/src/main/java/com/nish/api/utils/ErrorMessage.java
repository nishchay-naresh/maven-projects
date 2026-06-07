package com.nish.api.utils;

/**
 * This class contains error message constants used throughout the application
 * to handle authentication-related errors.
 * <p>
 * The constants in this class represent various error messages related to authentication
 * and JWT (JSON Web Token) validation. These messages are used across the application to
 * provide descriptive error responses when authentication fails or when invalid credentials
 * or JWT tokens are encountered.
 * </p>
 *
 * <p>
 * These messages are intended to be used to generate clear and consistent error responses
 * for users or systems interacting with the application.
 * </p>
 */
public class ErrorMessage {
  public static final String AUTHENTICATION_FAILED_ERROR_PREFIX = "Authentication failed:";
  public static final String MISSING_CREDENTIAL_ERROR = "Missing authentication credential, Please provide authentication detail properly";
  public static final String CREDENTIAL_MISMATCH_ERROR = "Wrong authentication credential, Username and password pair is not acceptable";
  public static final String INVALID_CREDENTIAL_ERROR = "Invalid credential, Please provide valid authentication detail";
  public static final String JWT_TOKEN_EXPIRED_ERROR = "Jwt Token Expired, Please re-authenticate to get new jwt token";
  public static final String INVALID_JWT_TOKEN_ERROR = "Invalid jwt token, Please provide valid jwt token";
}
