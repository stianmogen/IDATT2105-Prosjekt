import { useMutation, useInfiniteQuery, useQuery, useQueryClient, UseMutationResult } from 'react-query';
import API from 'api/api';
import { Room, RoomRequired, PaginationResponse, RequestResponse, RoomList, Registration } from 'types/Types';
import { getNextPaginationPage } from 'utils';
export const ROOM_QUERY_KEY = 'rooms';
export const ROOMS_QUERY_KEY = `rooms_list`;
export const ROOMS_QUERY_KEY_REGISTRATION = `${ROOM_QUERY_KEY}_registrations`;
export const MY_REGISTRATIONS = `my_registrations`;
export const SECTION_QUERY_KEY = 'sections';

/**
 * Get a specific room
 * @param id - Id of room
 */
export const useRoomById = (id: string) => {
  return useQuery<Room, RequestResponse>([ROOM_QUERY_KEY, id], () => API.getRoom(id), { enabled: id !== '' });
};

/**
 * Get all activities, paginated
 * @param filters - Filtering
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const useRooms = (filters?: any) => {
  return useInfiniteQuery<PaginationResponse<RoomList>, RequestResponse>(
    [ROOM_QUERY_KEY, filters],
    ({ pageParam = 0 }) => API.getRooms({ ...filters, page: pageParam }),
    {
      getNextPageParam: getNextPaginationPage,
    },
  );
};

export const useCreateRoom = (): UseMutationResult<Room, RequestResponse, RoomRequired, unknown> => {
  const queryClient = useQueryClient();
  return useMutation((newRoom: RoomRequired) => API.createRoom(newRoom), {
    onSuccess: (data) => {
      queryClient.invalidateQueries(ROOM_QUERY_KEY);
      queryClient.setQueryData([ROOM_QUERY_KEY, data.id], data);
    },
  });
};

export const useUpdateRoom = (id: number): UseMutationResult<Room, RequestResponse, RoomRequired, unknown> => {
  const queryClient = useQueryClient();
  return useMutation((updatedRoom: RoomRequired) => API.updateRoom(id, updatedRoom), {
    onSuccess: (data) => {
      queryClient.invalidateQueries(ROOM_QUERY_KEY);
      queryClient.setQueryData([ROOM_QUERY_KEY, id], data);
    },
  });
};

export const useDeleteRoom = (id: number): UseMutationResult<RequestResponse, RequestResponse, unknown, unknown> => {
  const queryClient = useQueryClient();
  return useMutation(() => API.deleteRoom(id), {
    onSuccess: () => {
      queryClient.invalidateQueries(ROOM_QUERY_KEY);
    },
  });
};

/**
 * Create a registration in an room
 * @param roomId - Id of room
 */
export const useCreateRoomRegistration = (roomId: string): UseMutationResult<Registration, RequestResponse, string, unknown> => {
  const queryClient = useQueryClient();
  return useMutation((userId) => API.createRegistration(roomId, userId), {
    onSuccess: (data) => {
      queryClient.invalidateQueries([ROOM_QUERY_KEY, roomId]);
      queryClient.invalidateQueries([ROOMS_QUERY_KEY, MY_REGISTRATIONS]);
      queryClient.setQueryData([ROOM_QUERY_KEY, roomId, ROOMS_QUERY_KEY_REGISTRATION, data.user.id], data);
    },
  });
};

/**
 * Get a user's registration at an room if it exists
 * @param roomId - Id of room
 * @param userId - Id of user
 */
export const useRoomRegistration = (roomId: string, userId: string) => {
  return useQuery<Registration, RequestResponse>([ROOM_QUERY_KEY, roomId, ROOMS_QUERY_KEY_REGISTRATION, userId], () => API.getRegistration(roomId, userId), {
    enabled: userId !== '',
    retry: false,
  });
};

/**
 * Delete a registration in an room
 * @param roomId - Id of room
 */
export const useDeleteRoomRegistration = (roomId: string): UseMutationResult<RequestResponse, RequestResponse, string, unknown> => {
  const queryClient = useQueryClient();
  return useMutation((userId: string) => API.deleteRegistration(roomId, userId), {
    onSuccess: () => {
      queryClient.invalidateQueries([ROOM_QUERY_KEY, roomId]);
      queryClient.invalidateQueries([ROOM_QUERY_KEY, roomId, ROOMS_QUERY_KEY_REGISTRATION]);
      queryClient.invalidateQueries([ROOMS_QUERY_KEY, MY_REGISTRATIONS]);
    },
  });
};
