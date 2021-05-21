import { useMemo } from 'react';
import { useMyReservatedSections } from 'hooks/Sections';

// Material UI
import { makeStyles } from '@material-ui/core/styles';

// Project components
import Pagination from 'components/layout/Pagination';
import NotFoundIndicator from 'components/miscellaneous/NotFoundIndicator';
import ReservationCard from 'components/layout/ReservationCard';

const useStyles = makeStyles((theme) => ({
  menu: {
    display: 'grid',
    gap: theme.spacing(1),
    gridTemplateColumns: '3fr 1fr',
    [theme.breakpoints.down('md')]: {
      gridTemplateColumns: '1fr',
      paddingBottom: theme.spacing(1),
    },
  },
  buttons: {
    marginBottom: theme.spacing(1),
  },
  button: {
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    height: 56,
  },
  list: {
    display: 'grid',
    gap: theme.spacing(1),
    gridTemplateColumns: '1fr 1fr',
    [theme.breakpoints.down('md')]: {
      gridTemplateColumns: '1fr',
    },
  },
}));

export type MyReservationsProps = {
  userId?: string;
};

const MyReservations = ({ userId }: MyReservationsProps) => {
  const classes = useStyles();

  const useHook = useMyReservatedSections;
  const { data, error, hasNextPage, fetchNextPage, isFetching } = useHook(userId);
  const reservations = useMemo(() => (data !== undefined ? data.pages.map((page) => page.content).flat(1) : []), [data]);
  const isEmpty = useMemo(() => !reservations.length && !isFetching, [reservations, isFetching]);

  return (
    <>
      <div className={classes.menu}></div>

      <Pagination fullWidth hasNextPage={hasNextPage} isLoading={isFetching} nextPage={() => fetchNextPage()}>
        {isEmpty && <NotFoundIndicator header={error?.message || 'Fant ingen reservasjoner'} />}
        <div className={classes.list}>
          {reservations.map((reservation) => (
            <ReservationCard fullHeight key={reservation.id} reservation={reservation} />
          ))}
        </div>
      </Pagination>
    </>
  );
};

export default MyReservations;
