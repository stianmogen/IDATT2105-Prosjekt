import { useInfiniteQuery, useQuery } from 'react-query';
import API from 'api/api';
import { PaginationResponse, RequestResponse, SectionsList, Sections } from 'types/Types';
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
export const useSectionById = (id: string) => {
  return useQuery<Sections, RequestResponse>([SECTION_QUERY_KEY, id], () => API.getSection(id), { enabled: id !== '' });
};

/**
 * Get all sections, paginated
 * @param filters - Filtering
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const useSections = (filters?: any) => {
  return useInfiniteQuery<PaginationResponse<SectionsList>, RequestResponse>(
    [SECTION_QUERY_KEY, filters],
    ({ pageParam = 0 }) => API.getSections({ ...filters, page: pageParam }),
    {
      getNextPageParam: getNextPaginationPage,
    },
  );
};
