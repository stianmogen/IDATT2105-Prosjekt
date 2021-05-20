import { useMutation, useInfiniteQuery, useQuery, useQueryClient, UseMutationResult } from 'react-query';
import API from 'api/api';
import { Room, RoomRequired, PaginationResponse, RequestResponse } from 'types/Types';

export const EXPORT_QUERY_KEY = 'rooms';

/**
 * Get a specific activity
 * @param id - Id of activity
 */
export const useRoomById = (id: string) => {
  return useQuery<Room, RequestResponse>([EXPORT_QUERY_KEY, id], () => API.getRoom(id), { enabled: id !== '' });
};

export const useRooms = () => {
  return useInfiniteQuery<PaginationResponse<Room>, RequestResponse>([EXPORT_QUERY_KEY], ({ pageParam = 1 }) => API.getRooms({ page: pageParam }), {
    getNextPageParam: (lastPage) => lastPage.next,
  });
};

export const useCreateRoom = (): UseMutationResult<Room, RequestResponse, RoomRequired, unknown> => {
  const queryClient = useQueryClient();
  return useMutation((newRoom: RoomRequired) => API.createRoom(newRoom), {
    onSuccess: (data) => {
      queryClient.invalidateQueries(EXPORT_QUERY_KEY);
      queryClient.setQueryData([EXPORT_QUERY_KEY, data.id], data);
    },
  });
};

export const useUpdateRoom = (id: number): UseMutationResult<Room, RequestResponse, RoomRequired, unknown> => {
  const queryClient = useQueryClient();
  return useMutation((updatedRoom: RoomRequired) => API.updateRoom(id, updatedRoom), {
    onSuccess: (data) => {
      queryClient.invalidateQueries(EXPORT_QUERY_KEY);
      queryClient.setQueryData([EXPORT_QUERY_KEY, id], data);
    },
  });
};

export const useDeleteRoom = (id: number): UseMutationResult<RequestResponse, RequestResponse, unknown, unknown> => {
  const queryClient = useQueryClient();
  return useMutation(() => API.deleteRoom(id), {
    onSuccess: () => {
      queryClient.invalidateQueries(EXPORT_QUERY_KEY);
    },
  });
};
