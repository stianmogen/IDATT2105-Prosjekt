/* eslint-disable @typescript-eslint/no-explicit-any */
import { IFetch } from 'api/fetch';
import { LoginRequestResponse, Activity, ActivityRequired, PaginationResponse, RequestResponse, User, UserCreate } from 'types/Types';

export default {
  // Auth
  createUser: (item: UserCreate) => IFetch<RequestResponse>({ method: 'POST', url: 'user/', data: item, withAuth: false }),
  authenticate: (username: string, password: string) =>
    IFetch<LoginRequestResponse>({
      method: 'POST',
      url: 'auth/login/',
      data: { user_id: username, password: password },
      withAuth: false,
    }),
  forgotPassword: (email: string) => IFetch<RequestResponse>({ method: 'POST', url: 'auth/password/reset/', data: { email: email }, withAuth: false }),

  // Activity
  getActivity: (id: number) => IFetch<Activity>({ method: 'GET', url: `activities/${String(id)}/` }),
  getActivities: (filters?: any) => IFetch<PaginationResponse<Activity>>({ method: 'GET', url: `activities/`, data: filters || {} }),
  createActivity: (item: ActivityRequired) => IFetch<Activity>({ method: 'POST', url: `activities/`, data: item }),
  updateActivity: (id: number, item: ActivityRequired) => IFetch<Activity>({ method: 'PUT', url: `activities/${String(id)}/`, data: item }),
  deleteActivity: (id: number) => IFetch<RequestResponse>({ method: 'DELETE', url: `activities/${String(id)}/` }),

  // User
  getUser: () => IFetch<User>({ method: 'GET', url: `user/userdata/` }),
  getUsers: (filters?: any) => IFetch<PaginationResponse<User>>({ method: 'GET', url: `user/`, data: filters || {} }),
  updateUser: (userName: string, item: Partial<User>) => IFetch<User>({ method: 'PUT', url: `user/${userName}/`, data: item }),
};
