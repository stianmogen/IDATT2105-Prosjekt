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
  userId: string;
  firstName: string;
  surname: string;
  email: string;
};
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
