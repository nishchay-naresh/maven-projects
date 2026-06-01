package com.nish.api.exception;

import com.nish.api.utils.AppStatusCode;

import java.io.Serial;

/**
 * Exception class representing errors that occur within the cache API.
 * <p>
 * This custom exception is used to signal various error conditions related to cache operations.
 * It extends {@link RuntimeException} and includes an associated {@link AppStatusCode} to
 * categorize the error, which can be used to provide detailed status information about the error.
 * </p>
 *
 * <p>
 * The exception can be created with different constructors to allow for passing an error message,
 * a cause, or a specific {@link AppStatusCode} to categorize the error.
 * </p>
 *
 * <p>
 * The {@link AppStatusCode} helps in categorizing the error based on the application status codes
 * defined in the system. The exception message provides additional details about the cause of the
 * exception.
 * </p>
 */
public class CacheApiException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -9083647126141368734L;

  private final AppStatusCode appStatusCode;

  /**
   * Constructs a new {@link CacheApiException} with the specified {@link AppStatusCode} and cause.
   *
   * @param appStatusCode the {@link AppStatusCode} associated with the error.
   * @param cause         the cause of the exception (can be {@code null}).
   */
  public CacheApiException(AppStatusCode appStatusCode, Throwable cause) {
    super(appStatusCode.name(), cause);
    this.appStatusCode = appStatusCode;
  }

  /**
   * Constructs a new {@link CacheApiException} with the specified {@link AppStatusCode}.
   *
   * @param appStatusCode the {@link AppStatusCode} associated with the error.
   */
  public CacheApiException(AppStatusCode appStatusCode) {
    super(appStatusCode.name());
    this.appStatusCode = appStatusCode;
  }

  /**
   * Constructs a new {@link CacheApiException} with the specified {@link AppStatusCode} and detail message.
   *
   * @param appStatusCode the {@link AppStatusCode} associated with the error.
   * @param message       the detail message (can be {@code null}).
   */
  public CacheApiException(AppStatusCode appStatusCode, String message) {
    super(message);
    this.appStatusCode = appStatusCode;
  }

  /**
   * Constructs a new {@link CacheApiException} with the specified detail message.
   * The {@link AppStatusCode} is set to {@link AppStatusCode#APP_STATUS_ABSENT}.
   *
   * @param message the detail message (can be {@code null}).
   */
  public CacheApiException(String message) {
    super(message);
    this.appStatusCode = AppStatusCode.APP_STATUS_ABSENT;
  }

  /**
   * Constructs a new {@link CacheApiException} with the specified detail message and cause.
   * The {@link AppStatusCode} is set to {@link AppStatusCode#APP_STATUS_ABSENT}.
   *
   * @param message the detail message (can be {@code null}).
   * @param cause   the cause of the exception (can be {@code null}).
   */
  public CacheApiException(String message, Throwable cause) {
    super(message, cause);
    this.appStatusCode = AppStatusCode.APP_STATUS_ABSENT;
  }

  public AppStatusCode getAppStatusCode() {
    return appStatusCode;
  }
}
