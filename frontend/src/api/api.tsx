/* eslint-disable @typescript-eslint/no-explicit-any */
import { IFetch } from 'api/fetch';
import { LoginRequestResponse, Room, RoomRequired, PaginationResponse, RequestResponse, User, UserCreate } from 'types/Types';

export default {
  // Auth
  createUser: (item: UserCreate) => IFetch<RequestResponse>({ method: 'POST', url: 'user/', data: item, withAuth: false }),
  authenticate: (email: string, password: string) =>
    IFetch<LoginRequestResponse>({
      method: 'POST',
      url: 'auth/login/',
      data: { user_id: email, password: password },
      withAuth: false,
    }),
  forgotPassword: (email: string) => IFetch<RequestResponse>({ method: 'POST', url: 'auth/password/reset/', data: { email: email }, withAuth: false }),

  // Room
  getRoom: (id: number) => IFetch<Room>({ method: 'GET', url: `rooms/${String(id)}/` }),
  getRooms: (filters?: any) => IFetch<PaginationResponse<Room>>({ method: 'GET', url: `rooms/`, data: filters || {} }),
  createRoom: (item: RoomRequired) => IFetch<Room>({ method: 'POST', url: `rooms/`, data: item }),
  updateRoom: (id: number, item: RoomRequired) => IFetch<Room>({ method: 'PUT', url: `rooms/${String(id)}/`, data: item }),
  deleteRoom: (id: number) => IFetch<RequestResponse>({ method: 'DELETE', url: `rooms/${String(id)}/` }),

  // User
  getUser: () => IFetch<User>({ method: 'GET', url: `user/userdata/` }),
  getUsers: (filters?: any) => IFetch<PaginationResponse<User>>({ method: 'GET', url: `user/`, data: filters || {} }),
  updateUser: (userName: string, item: Partial<User>) => IFetch<User>({ method: 'PUT', url: `user/${userName}/`, data: item }),
};
