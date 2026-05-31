package com.nish.api.utils;

import org.springframework.http.HttpStatus;

/**
 * Enum representing various application status codes and their corresponding HTTP status codes and descriptions.
 * <p>
 * This enum is used to define various application-level status codes that are mapped to HTTP status codes.
 * Each enum constant contains an HTTP status code and a description that explains the meaning of the status.
 * These status codes are used to communicate the result of an operation in the application.
 * </p>
 */
public enum AppStatusCode {
  //OK
  SUCCESS(0, HttpStatus.OK, "Success"),
  //CREATED
  CREATED(0, HttpStatus.CREATED, "Created"),
  //NO_CONTENT
  APP_STATUS_ABSENT(0, HttpStatus.NO_CONTENT, "Absent"),
  //INPUT_VALIDATION_STATUS, Don't change code of this status
  VALID_INPUT(-1, HttpStatus.OK, "Valid input."),
  //BAD_REQUEST
  MISSING_RECORD(-1, HttpStatus.BAD_REQUEST, "Record is missing."),
  MISSING_KEY(-1, HttpStatus.BAD_REQUEST, "Key is missing."),
  MISSING_KEY_TYPE(-1, HttpStatus.BAD_REQUEST, "Key type is missing."),
  MISSING_VALUE(-1, HttpStatus.BAD_REQUEST, "Value is missing."),
  MISSING_CELLS(-1, HttpStatus.BAD_REQUEST, "Cells are missing."),
  MISSING_CELL_TYPE(-1, HttpStatus.BAD_REQUEST, "Cell type is missing."),
  MISSING_CELL_VALUE(-1, HttpStatus.BAD_REQUEST, "Cell value is missing."),
  URL_KEY_MISMATCH(-1, HttpStatus.BAD_REQUEST, "The key in the URL does not match the key provided in the body."),
  INVALID_KEY_OR_TYPE(-1, HttpStatus.BAD_REQUEST, "The key or its type provided is invalid."),
  INVALID_VALUE_OR_TYPE(-1, HttpStatus.BAD_REQUEST, "The value or its type provided is invalid."),
  INVALID_CURRENT_OR_TYPE(-1, HttpStatus.BAD_REQUEST, "The current value or its type provided is invalid."),
  INVALID_CAS_VALUE(-1, HttpStatus.BAD_REQUEST, "CAS value provided is invalid."),
  INPUT_VALIDATION_STATUS(0, HttpStatus.BAD_REQUEST, "Invalid input."),
  INVALID_DATASET_NAME(1, HttpStatus.BAD_REQUEST, "Invalid dataset name, Please provide a valid dataset name."),
  INVALID_WHITELISTED_DATASET(2, HttpStatus.BAD_REQUEST, "Invalid whitelisted datasets. Please set ',' separated valid whitelisted datasets in '" + Constants.DATASET_WHITELIST + "' property."),
  INVALID_RECORD_PARAM(3, HttpStatus.BAD_REQUEST, "Invalid record parameters."),
  INVALID_URL(4, HttpStatus.BAD_REQUEST, "Invalid URL."),
  INVALID_KEY(5, HttpStatus.BAD_REQUEST, "Invalid key."),
  INVALID_VALUE(6, HttpStatus.BAD_REQUEST, "Invalid value."),
  INVALID_REQUEST_BODY(7, HttpStatus.BAD_REQUEST, "Required request body is invalid."),
  CELL_TYPE_MISMATCH(8, HttpStatus.BAD_REQUEST, "Existing cell type mismatch."),
  INVALID_CACHE_MANAGER_NAME(9, HttpStatus.BAD_REQUEST, "Invalid cache manager name, Please provide a valid cache manager name."),
  INVALID_CACHE_ALIAS(10, HttpStatus.BAD_REQUEST, "Invalid cache alias, Please provide a valid cache alias."),
  DUPLICATE_KEY_IN_CELLS(11, HttpStatus.BAD_REQUEST, "Duplicate cell provided."),
  INVALID_CELL_VALUE(12, HttpStatus.BAD_REQUEST, "Invalid cell value, cell can't be null."),
  //CONFLICT
  INPUT_DATA_CONFLICT(0, HttpStatus.CONFLICT, "Input data conflict when compared with the database."),
  RECORD_EXISTS_CONFLICT(1, HttpStatus.CONFLICT, "Record already exists."),
  CELL_EXISTS_CONFLICT(2, HttpStatus.CONFLICT, "Cell already exists."),
  KEY_TYPE_CONFLICT(3, HttpStatus.CONFLICT, "Key type conflict."),
  CELL_TYPE_CONFLICT(4, HttpStatus.CONFLICT, "Cell type conflict."),
  CELL_CURRENT_VALUE_CONFLICT(5, HttpStatus.CONFLICT, "Current cell value conflict."),
  //FORBIDDEN
  FORBIDDEN_DATASET(0, HttpStatus.FORBIDDEN, "Dataset not found or access denied."),
  //NOT_FOUND
  DATASET_MANAGER_NOT_FOUND(0, HttpStatus.NOT_FOUND, "Dataset manager is not configured, Please configure it first before making any query to TCStore."),
  CACHEMANAGER_NOT_FOUND(1, HttpStatus.NOT_FOUND, "CacheManager not found."),
  DATASET_NOT_FOUND(2, HttpStatus.NOT_FOUND, "Dataset not found or access denied."),
  CACHE_NOT_FOUND(3, HttpStatus.NOT_FOUND, "Cache not found."),
  RECORD_NOT_FOUND(4, HttpStatus.NOT_FOUND, "Record not found."),
  CELL_NOT_FOUND(5, HttpStatus.NOT_FOUND, "Cell not found."),
  //NOT_ACCEPTABLE
  CONTENT_TYPE_MISMATCH(0, HttpStatus.NOT_ACCEPTABLE, "Only 'application/json' content type is supported."),
  //INTERNAL_SERVER_ERROR
  INTERNAL_SERVER_ERROR(0, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
  DATASET_MANAGER_CONNECTION_FAILED(1, HttpStatus.INTERNAL_SERVER_ERROR, "Error while creating the dataset manager.");

  private final int code;
  private final String description;
  private final HttpStatus httpStatus;

  /**
   * Constructs an {@link AppStatusCode} enum instance with the specified code, HTTP status, and description.
   *
   * @param code        the unique status code
   * @param httpStatus  the associated HTTP status
   * @param description a description of the status
   */
  AppStatusCode(int code, HttpStatus httpStatus, String description) {
    this.code = code;
    this.httpStatus = httpStatus;
    this.description = description;
  }

  /**
   * Gets the unique status code for this application status.
   * <p>
   * The status code is calculated by multiplying the HTTP status code by a constant offset and adding the
   * individual status code value.
   * </p>
   *
   * @return the unique application status code
   */
  public int getCode() {
    return httpStatus.value() * Constants.APP_STATUS_CODE_OFFSET + code;
  }

  /**
   * Gets the description of the status code.
   *
   * @return the description of the status code
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the HTTP status associated with this status code.
   *
   * @return the HTTP status
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
