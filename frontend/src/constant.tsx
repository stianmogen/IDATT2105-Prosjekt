export const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/';

export const ACCESS_TOKEN = 'access_token';
export const REFRESH_TOKEN = 'refresh_token';
export const EMAIL_REGEX = RegExp(
  // eslint-disable-next-line no-useless-escape
  /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
);

// 15 minutes
export const ACCESS_TOKEN_DURATION = 1000 * 60 * 15;
// 24 hours
export const REFRESH_TOKEN_DURATION = 1000 * 60 * 60 * 24;
