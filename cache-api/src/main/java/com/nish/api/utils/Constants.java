package com.nish.api.utils;

/**
 * This class contains constant values used throughout the application.
 * <p>
 * The constants in this class represent system property names, default values,
 * API versioning information, and other configuration-related values required
 * for the proper functioning of the system. They are used across various components
 * of the application to maintain consistency and avoid hardcoding values.
 * </p>
 */
public class Constants {
  //Prefix
  public static final String APP_PREFIX = "trg";
  public static final String API_SECURITY_PREFIX = APP_PREFIX + ".security";
  public static final String TRG_CONNECTION_URL = "trg.connection.url";
  public static final String TRG_CONNECTION_TIMEOUT = "trg.connection.timeout";

  //App Properties
  public static final String CACHEMANAGER_WHITELIST = "trg.cacheManager.whitelist";
  public static final String CACHEMANAGER_CONFIG_PATH = "trg.cacheManager.config.directory";
  public static final String CACHEMANAGER_WHITELIST_ENABLED = "trg.cacheManager.whitelist.enabled";
  public static final String CACHEMANAGER_SUPPORTED_DATATYPE = "trg.cacheManager.supported.datatype";

  public static final String DATASET_WHITELIST = "trg.datasets.whitelist";
  public static final String DATASET_WHITELIST_ENABLED = "trg.datasets.whitelist.enabled";

  //Security Properties
  public static final String AUDIT_DIRECTORY = "trg.security.audit.directory";
  //Update APP_SECURITY_ROOT_DIRECTORY property name in this class com.terracottatech.security.spring.SecurityEnabledCondition
  // too if this property name changed
  public static final String APP_SECURITY_ROOT_DIRECTORY = "trg.security.root.directory";
  public static final String TRG_CLUSTER_SECURE_DIR = "trg.security.cluster.secure.dir";
  public static final String IS_HTTPS_ENABLED = "trg.security.https.enabled";
  public static final String AUTHENTICATION_SCHEME = "trg.security.authentication.scheme";
  public static final String AUTHORIZATION_SCHEME = "trg.security.authorization.scheme";
  public static final String JWT_EXPIRY_TIME = "trg.jwt.expiry.duration";
  public static final String DEFAULT_HTTP_FIREWALL_ENABLED = "trg.http.firewall";

  //Default values
  public static final String APP_NAME_VARIABLE = "spring.application.name";
  public static final String TRG_AUDIT_SECURE_DIRECTORY = "trg-audit-logs";
  public static final String TRG_AUDIT_FILENAME = "trg-audit";
  public static final String API_VERSION = "X-API-Version";
  public static final String BEARER_TOKEN_PREFIX = "Bearer ";
  public static final String JWT_EXPIRY_DEFAULT = "600";
  public static final String DATASET_WHITELIST_ENABLED_DEFAULT = "false";
  public static final String CURRENT_API_VERSION = "1";
  public static final String FIRST_API_VERSION = "1";
  public static final int APP_STATUS_CODE_OFFSET = 100;
  public static final String APP_NAME = "Terracotta REST Gateway";
}
