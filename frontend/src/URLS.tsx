const AUTH = '/auth/';

export const AUTH_RELATIVE_ROUTES = {
  LOGIN: '',
  SIGNUP: 'sign-up/',
};

export const AUTH_ROUTES = {
  LOGIN: AUTH + AUTH_RELATIVE_ROUTES.LOGIN,
  SIGNUP: AUTH + AUTH_RELATIVE_ROUTES.SIGNUP,
};

export default {
  LANDING: '/',
  ROOMS: '/book-room/',
  BOOKINGS: '/my-bookings',
  PROFILE: '/profile/',
  USERS: '/users/',
  ADMIN: '/admin-page/',
  ...AUTH_ROUTES,
};
