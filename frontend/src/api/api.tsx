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
  Reservation,
  Sections,
  SectionsList,
  BookingCreate,
  ReservationList,
} from 'types/Types';
import { setCookie } from './cookie';

export const USERS = 'users';
export const ME = 'me';
export const AUTH = 'auth';
export const REGISTRATIONS = 'reservations';
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
  changeRole: (email: string, role: string) => IFetch<RequestResponse>({ method: 'PUT', url: `admin/users/change-role/`, data: { email, role } }),

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
  getReservations: (roomId: string, filters?: any) =>
    IFetch<PaginationResponse<Reservation>>({ method: 'GET', url: `${ROOMS}/${roomId}/${REGISTRATIONS}/`, data: filters || {} }),
  getReservation: (roomId: string, userId: string) => IFetch<Reservation>({ method: 'GET', url: `${ROOMS}/${roomId}/${REGISTRATIONS}/${userId}/` }),
  createReservation: (item: BookingCreate, roomId: string) => IFetch<Reservation>({ method: 'POST', url: `${ROOMS}/${roomId}/${REGISTRATIONS}/`, data: item }),
  deleteReservation: (roomId: string, userId: string) => IFetch<RequestResponse>({ method: 'DELETE', url: `${ROOMS}/${roomId}/${REGISTRATIONS}/${userId}/` }),

  // Section
  getSection: (id: string) => IFetch<Sections>({ method: 'GET', url: `${SECTIONS}/${id}/` }),
  getMyReservatedSections: (filters?: any) =>
    IFetch<PaginationResponse<ReservationList>>({ method: 'GET', url: `users/${ME}/reservations/`, data: filters || {} }),

  getSections: (roomId: string, filters?: any) =>
    IFetch<PaginationResponse<SectionsList>>({ method: 'GET', url: `${ROOMS}/${roomId}/${SECTIONS}/`, data: filters || {} }),
};
