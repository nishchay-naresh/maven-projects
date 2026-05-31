package com.nish.api.model;

import com.nish.api.utils.AppStatusCode;

import java.util.List;

/**
 * Represents the response for the application status, encapsulating the status code,
 * status message, and additional context about errors (if any).
 * <p>
 * This class provides different constructors to initialize the status response,
 * allowing you to specify just the status code and message, or include additional
 * error context.
 * </p>
 * <p>
 * Implements the {@link Response} interface, providing standardized status information
 * for the application's API responses.
 */
public class AppStatusResponse implements Response {
  private final Integer statusCode;
  private final String status;
  private List<ErrorContext> context;

  /**
   * Constructs an {@code AppStatusResponse} with the given {@link AppStatusCode} and message.
   *
   * @param appStatus the application status code
   * @param message   the status message
   */
  public AppStatusResponse(AppStatusCode appStatus, String message) {
    this.statusCode = appStatus.getCode();
    this.status = message;
    this.context = null;
  }

  /**
   * Constructs an {@code AppStatusResponse} with the given {@link AppStatusCode}.
   * The status message will be the name of the provided {@link AppStatusCode}.
   *
   * @param appStatus the application status code
   */
  public AppStatusResponse(AppStatusCode appStatus) {
    this.statusCode = appStatus.getCode();
    this.status = appStatus.name();
    this.context = null;
  }

  /**
   * Constructs an {@code AppStatusResponse} with the given {@link AppStatusCode}
   * and a list of error contexts.
   *
   * @param appStatus the application status code
   * @param context   a list of {@link ErrorContext} objects containing additional
   *                  information about the error
   */
  public AppStatusResponse(AppStatusCode appStatus, List<ErrorContext> context) {
    this.statusCode = appStatus.getCode();
    this.context = context;
    this.status = appStatus.name();
  }

  /**
   * Default constructor, initializing the status to {@link AppStatusCode#SUCCESS}.
   */
  public AppStatusResponse() {
    this.statusCode = AppStatusCode.SUCCESS.getCode();
    this.status = AppStatusCode.SUCCESS.name();
    this.context = null;
  }

  public String getStatus() {
    return status;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public List<ErrorContext> getContext() {
    return context;
  }

  public void setContext(List<ErrorContext> context) {
    this.context = context;
  }

  /**
   * Returns the corresponding {@link AppStatusCode} based on the status code.
   *
   * @return the {@link AppStatusCode} associated with the status code
   */
  public AppStatusCode getAppStatusCode() {
    for (AppStatusCode status : AppStatusCode.values()) {
      if (status.getCode() == statusCode) {
        return status;
      }
    }
    return null;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (statusCode != null ? statusCode.hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AppStatusResponse that = (AppStatusResponse) o;

    if (status != null && statusCode != null) {
      return statusCode.equals(that.statusCode) && status.equals(that.status);
    }
    return status != null && status.equals(that.status);
  }
}
