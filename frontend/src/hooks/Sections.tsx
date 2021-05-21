import { useInfiniteQuery, useQuery } from 'react-query';
import API from 'api/api';
import { PaginationResponse, RequestResponse, SectionsList, Sections } from 'types/Types';
import { getNextPaginationPage } from 'utils';
export const ROOM_QUERY_KEY = 'rooms';
export const ROOMS_QUERY_KEY = `sections_list`;
export const ROOMS_QUERY_KEY_REGISTRATION = `${ROOM_QUERY_KEY}_reservation`;
export const MY_RESERVATION = `my_reservation`;
export const SECTION_QUERY_KEY = 'sections';
/**
 * Get a specific room
 * @param id - Id of room
 */
export const useSectionById = (id: string) => {
  return useQuery<Sections, RequestResponse>([SECTION_QUERY_KEY, id], () => API.getSection(id), { enabled: id !== '' });
};

/**
 * Get all sections, paginated
 * @param filters - Filtering
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const useSections = (userId: string, filters?: any) => {
  return useInfiniteQuery<PaginationResponse<SectionsList>, RequestResponse>(
    [SECTION_QUERY_KEY, userId, filters],
    ({ pageParam = 0 }) => API.getSections(userId, { ...filters, page: pageParam }),
    {
      getNextPageParam: getNextPaginationPage,
    },
  );
};

/**
 * Get reservations where the user is owner, paginated
 * @param filters - Filtering
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const useMyReservatedSections = (filters?: any) => {
  return useInfiniteQuery<PaginationResponse<SectionsList>, RequestResponse>(
    [ROOMS_QUERY_KEY_REGISTRATION, MY_RESERVATION, filters],
    ({ pageParam = 0 }) => API.getMyReservatedSections({ ...filters, page: pageParam }),
    {
      getNextPageParam: getNextPaginationPage,
    },
  );
};
