import { useMemo, useState } from 'react';
import Helmet from 'react-helmet';
import { useRooms } from 'hooks/Rooms';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Typography } from '@material-ui/core/';

// Project Components
import Navigation from 'components/navigation/Navigation';
import Pagination from 'components/layout/Pagination';
import NotFoundIndicator from 'components/miscellaneous/NotFoundIndicator';
import SearchBar from 'components/inputs/SearchBar';
import RoomCard from 'components/layout/RoomCard';
import MasonryGrid from 'components/layout/MasonryGrid';
import { Building } from 'types/Types';
const useStyles = makeStyles((theme) => ({
  root: {
    paddingBottom: theme.spacing(2),
  },
  list: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr 1fr',
    gap: theme.spacing(0, 1),
    [theme.breakpoints.down('lg')]: {
      gap: theme.spacing(1),
      gridTemplateColumns: '1fr',
    },
  },
  first: {
    gridColumn: 'span 3',
    [theme.breakpoints.down('lg')]: {
      gridColumn: 'span 1',
    },
  },
  wrapper: {
    marginTop: theme.spacing(2),
  },
  searchBtn: {
    borderRadius: 15,
  },
  title: {
    paddingBottom: theme.spacing(2),
  },
}));

export type RoomFilters = {
  startDate?: string;
  endDate?: string;
  amount?: number;
  building?: Building;
};

const Rooms = () => {
  const classes = useStyles();
  const [filters, setFilters] = useState<RoomFilters>({});
  const { data, error, hasNextPage, fetchNextPage, isFetching } = useRooms(filters);
  const rooms = useMemo(() => (data !== undefined ? data.pages.map((page) => page.content).flat(1) : []), [data]);
  const isEmpty = useMemo(() => !rooms.length && !isFetching, [rooms, isFetching]);
  return (
    <Navigation topbarVariant='filled'>
      <Helmet>
        <title>Book Room</title>
      </Helmet>
      <div className={classes.wrapper}>
        <Typography align='center' className={classes.title} variant='h1'>
          Book Room
        </Typography>
        <SearchBar updateFilters={setFilters} />
        <div className={classes.root}>
          {/* {isLoading && <ListItemLoading />} */}
          <Pagination fullWidth hasNextPage={hasNextPage} isLoading={isFetching} nextPage={() => fetchNextPage()}>
            <MasonryGrid>
              {isEmpty && <NotFoundIndicator header={error?.message || 'Couldnt find any rooms'} />}
              {rooms.map((room) => (
                <RoomCard key={room.id} room={room} />
              ))}
            </MasonryGrid>
          </Pagination>
          {/* {isFetching && <ListItemLoading />} */}
        </div>
      </div>
    </Navigation>
  );
};

export default Rooms;
