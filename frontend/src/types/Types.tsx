export type RequestResponse = {
  detail: string;
};

export type LoginRequestResponse = {
  token: string;
};

export type PaginationResponse<T> = {
  totalElements: number;
  totalPages: number;
  number: number;
  next: number | null;
  previous: number | null;
  content: Array<T>;
  empty: boolean;
  last: boolean;
};

export type User = {
  user_id: string;
  first_name: string;
  last_name: string;
  email: string;
};
export type UserCreate = Pick<User, 'email' | 'first_name' | 'last_name' | 'user_id'> & {
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
