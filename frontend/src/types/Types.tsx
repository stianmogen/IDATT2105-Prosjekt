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

export type RoomRequired = Partial<Room> & Pick<Room, 'id' | 'name' | 'building' | 'level' | 'sections'>;

export type Room = {
  id: string;
  building: Building;
  level: number;
  name: string;
  sections: Array<Sections>;
};

export type RoomList = Pick<Room, 'id' | 'name' | 'building' | 'level' | 'sections'>;

export type BuildingRequired = Partial<Building> & Pick<Building, 'id' | 'name' | 'address' | 'levels'>;

export type Building = {
  id: string;
  name: string;
  address: string;
  levels: number;
};

export type BuildingList = Pick<Building, 'id' | 'name' | 'address' | 'levels'>;

export type SectionsRequired = Partial<Sections> & Pick<Sections, 'id' | 'name' | 'capacity'>;

export type Sections = {
  id: string;
  name: string;
  capacity: number;
};

export type SectionsList = Pick<Sections, 'id' | 'name' | 'capacity'>;

export type Registration = {
  id: string;
  user: User;
  sections: Array<Sections>;
  description: string;
  participants: number;
  startTime: string;
  endTime: string;
};

export type ReservationList = Pick<Registration, 'id' | 'user' | 'sections' | 'description' | 'participants' | 'startTime' | 'endTime'> &
  Pick<SectionsList, 'id' | 'name' | 'capacity'>;
