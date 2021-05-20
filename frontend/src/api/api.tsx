/* eslint-disable @typescript-eslint/no-explicit-any */
import { IFetch } from 'api/fetch';
import { ACCESS_TOKEN, ACCESS_TOKEN_DURATION, REFRESH_TOKEN, REFRESH_TOKEN_DURATION } from 'constant';
import { logout } from 'hooks/User';
import {
  LoginRequestResponse,
  Room,
  RoomRequired,
  PaginationResponse,
  RequestResponse,
  User,
  UserCreate,
  RefreshTokenResponse,
  UserList,
  RoomList,
  Registration,
  Sections,
  SectionsList,
} from 'types/Types';
import { setCookie } from './cookie';

export const USERS = 'users';
export const ME = 'me';
export const AUTH = 'auth';
export const REGISTRATIONS = 'registrations';
export const ROOMS = 'rooms';
export const SECTIONS = 'sections';
export default {
  // Auth
  createUser: (item: UserCreate) => IFetch<RequestResponse>({ method: 'POST', url: `${USERS}/`, data: item, withAuth: false, tryAgain: false }),
  authenticate: (email: string, password: string) =>
    IFetch<LoginRequestResponse>({
      method: 'POST',
      url: `${AUTH}/login`,
      data: { email, password },
      withAuth: false,
      tryAgain: false,
    }),
  refreshAccessToken: () =>
    IFetch<RefreshTokenResponse>({ method: 'GET', url: `${AUTH}/refresh-token/`, refreshAccess: true, withAuth: false })
      .then((tokens) => {
        setCookie(ACCESS_TOKEN, tokens.token, ACCESS_TOKEN_DURATION);
        setCookie(REFRESH_TOKEN, tokens.refreshToken, REFRESH_TOKEN_DURATION);
        return tokens;
      })
      .catch((e) => {
        logout();
        throw e;
      }),
  changePassword: (oldPassword: string, newPassword: string) =>
    IFetch<RequestResponse>({ method: 'POST', url: `${AUTH}/change-password/`, data: { oldPassword, newPassword } }),
  deleteUser: () => IFetch<RequestResponse>({ method: 'DELETE', url: `${USERS}/me/` }),

  // Room
  getRoom: (id: string) => IFetch<Room>({ method: 'GET', url: `${ROOMS}/${id}/` }),
  getRooms: (filters?: any) => IFetch<PaginationResponse<RoomList>>({ method: 'GET', url: `${ROOMS}/`, data: filters || {} }),
  createRoom: (item: RoomRequired) => IFetch<Room>({ method: 'POST', url: `rooms/`, data: item }),
  updateRoom: (id: number, item: RoomRequired) => IFetch<Room>({ method: 'PUT', url: `rooms/${String(id)}/`, data: item }),
  deleteRoom: (id: number) => IFetch<RequestResponse>({ method: 'DELETE', url: `rooms/${String(id)}/` }),

  // User
  getUser: (userId?: string) => IFetch<User>({ method: 'GET', url: `${USERS}/${userId || ME}/` }),
  getUsers: (filters?: any) => IFetch<PaginationResponse<UserList>>({ method: 'GET', url: `${USERS}/`, data: filters || {} }),
  updateUser: (userId: string, item: Partial<User>) => IFetch<User>({ method: 'PUT', url: `${USERS}/${userId}/`, data: item }),

  // Room registrations
  getRegistrations: (activityId: string, filters?: any) =>
    IFetch<PaginationResponse<Registration>>({ method: 'GET', url: `${ROOMS}/${activityId}/${REGISTRATIONS}/`, data: filters || {} }),
  getRegistration: (activityId: string, userId: string) => IFetch<Registration>({ method: 'GET', url: `${ROOMS}/${activityId}/${REGISTRATIONS}/${userId}/` }),
  createRegistration: (activityId: string, userId: string) =>
    IFetch<Registration>({ method: 'POST', url: `${ROOMS}/${activityId}/${REGISTRATIONS}/`, data: { id: userId } }),
  deleteRegistration: (activityId: string, userId: string) =>
    IFetch<RequestResponse>({ method: 'DELETE', url: `${ROOMS}/${activityId}/${REGISTRATIONS}/${userId}/` }),

  // Section
  getSection: (id: string) => IFetch<Sections>({ method: 'GET', url: `${SECTIONS}/${id}/` }),
  getSections: (filters?: any) => IFetch<PaginationResponse<SectionsList>>({ method: 'GET', url: `${SECTIONS}/`, data: filters || {} }),
};
