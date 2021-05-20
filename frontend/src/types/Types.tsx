export type RequestResponse = {
  message: string;
};

export type LoginRequestResponse = {
  token: string;
  refreshToken: string;
};
export type RefreshTokenResponse = {
  token: string;
  refreshToken: string;
};

export type PaginationResponse<T> = {
  totalElements: number;
  totalPages: number;
  number: number;
  content: Array<T>;
  empty: boolean;
  last: boolean;
};

export type User = {
  id: string;
  firstName: string;
  surname: string;
  email: string;
  phone: number;
};
export type UserList = Pick<User, 'id' | 'firstName' | 'surname' | 'email' | 'phone'>;
export type UserCreate = Pick<User, 'email' | 'firstName' | 'surname'> & {
  password: string;
};

export type RoomRequired = Partial<Room> & Pick<Room, 'id' | 'name' | 'building' | 'level' | 'capacity'>;

export type Room = {
  id: string;
  building: string;
  level: number;
  name: string;
  capacity: number;
};

export type RoomList = Pick<Room, 'id' | 'name' | 'building' | 'level' | 'capacity'>;

export type Registration = {
  user: User;
};
