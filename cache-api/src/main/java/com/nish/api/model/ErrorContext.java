package com.nish.api.model;

/**
 * A record representing the context of an error, including details such as the cell
 * where the error occurred, the value associated with the error, the type of the value,
 * and the error message.
 * <p>
 * This class is used to encapsulate error-specific information, providing a clear structure
 * for tracking and reporting errors.
 * </p>
 *
 * @param cell  the cell where the error occurred (optional)
 * @param value the value associated with the error (optional)
 * @param type  the type of the value (optional)
 * @param error the error message describing the issue
 */
public record ErrorContext(String cell, Object value, String type, String error) {

  /**
   * Creates an {@link ErrorContext} instance with only the error message.
   * <p>
   * This version sets all other fields (`cell`, `value`, `type`) to {@code null}.
   * </p>
   *
   * @param error the error message describing the issue
   * @return a new {@link ErrorContext} instance with the provided error message
   */
  public static ErrorContext of(String error) {
    return new ErrorContext(null, null, null, error);
  }

  /**
   * Creates an {@link ErrorContext} instance with the specified cell, value, and error message.
   * <p>
   * The type field is set to {@code null} in this version.
   * </p>
   *
   * @param cell  the cell where the error occurred
   * @param value the value associated with the error
   * @param error the error message describing the issue
   * @return a new {@link ErrorContext} instance with the provided cell, value, and error message
   */
  public static ErrorContext of(String cell, Object value, String error) {
    return new ErrorContext(cell, value, null, error);
  }

  /**
   * Creates an {@link ErrorContext} instance with the specified cell and error message.
   * <p>
   * The type field is set to {@code null} in this version.
   * </p>
   *
   * @param cell  the cell where the error occurred
   * @param error the error message describing the issue
   * @return a new {@link ErrorContext} instance with the provided cell and error message
   */
  public static ErrorContext of(String cell, String error) {
    return new ErrorContext(cell, null, null, error);
  }

  /**
   * Creates an {@link ErrorContext} instance with the specified value and error message.
   * <p>
   * The cell and type fields are set to {@code null} in this version.
   * </p>
   *
   * @param value the value associated with the error
   * @param error the error message describing the issue
   * @return a new {@link ErrorContext} instance with the provided value and error message
   */
  public static ErrorContext of(Object value, String error) {
    return new ErrorContext(null, value, null, error);
  }
}
