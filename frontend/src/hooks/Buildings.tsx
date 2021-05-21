import { useInfiniteQuery, useQuery } from 'react-query';
import API from 'api/api';
import { PaginationResponse, RequestResponse, BuildingList, Building } from 'types/Types';
import { getNextPaginationPage } from 'utils';
export const ROOM_QUERY_KEY = 'rooms';
export const ROOMS_QUERY_KEY = `rooms_list`;
export const ROOMS_QUERY_KEY_REGISTRATION = `${ROOM_QUERY_KEY}_registrations`;
export const MY_REGISTRATIONS = `my_registrations`;
export const BUILDING_QUERY_KEY = 'buildings';
/**
 * Get a specific room
 * @param id - Id of room
 */
export const useBuildingById = (id: string) => {
  return useQuery<Building, RequestResponse>([BUILDING_QUERY_KEY, id], () => API.getBuilding(id), { enabled: id !== '' });
};

/**
 * Get all buildings, paginated
 * @param filters - Filtering
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const useBuildings = (filters?: any) => {
  return useInfiniteQuery<PaginationResponse<BuildingList>, RequestResponse>(
    [BUILDING_QUERY_KEY, filters],
    ({ pageParam = 0 }) => API.getBuildings({ ...filters, page: pageParam }),
    {
      getNextPageParam: getNextPaginationPage,
    },
  );
};
