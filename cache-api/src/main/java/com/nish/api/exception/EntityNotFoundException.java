package com.nish.api.exception;

import com.nish.api.utils.AppStatusCode;

import java.io.Serial;

/**
 * Exception thrown when an entity is not found in the system.
 * <p>
 * This exception extends {@link RestGatewayException} and is used to signal that an entity
 * could not be found in the data store or system. It is typically used when an attempt to retrieve
 * or access an entity by its identifier fails because the entity does not exist.
 * </p>
 */
public class EntityNotFoundException extends RestGatewayException {

  @Serial
  private static final long serialVersionUID = 3937731587078422165L;

  /**
   * Constructs a new {@link EntityNotFoundException} with the specified {@link AppStatusCode}.
   *
   * @param appStatus the {@link AppStatusCode} that provides the status and description of the error
   */
  public EntityNotFoundException(AppStatusCode appStatus) {
    super(appStatus);
  }

  /**
   * Constructs a new {@link EntityNotFoundException} with the specified {@link AppStatusCode} and cause.
   *
   * @param appStatus the {@link AppStatusCode} that provides the status and description of the error
   * @param cause     the cause of the exception (typically an underlying exception)
   */
  public EntityNotFoundException(AppStatusCode appStatus, Throwable cause) {
    super(appStatus, cause);
  }
}
