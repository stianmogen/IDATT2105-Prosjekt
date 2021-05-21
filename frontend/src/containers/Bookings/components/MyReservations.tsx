import { useState, useMemo } from 'react';
import { useMyReservatedSections } from 'hooks/Sections';

// Material UI
import { makeStyles } from '@material-ui/core/styles';
import { Button, ButtonGroup, Collapse, Hidden, TextField, MenuItem } from '@material-ui/core';

// Icons
import FutureIcon from '@material-ui/icons/FastForwardRounded';
import HistoryIcon from '@material-ui/icons/HistoryRounded';
import LikedIcon from '@material-ui/icons/FavoriteRounded';

// Project components
import Pagination from 'components/layout/Pagination';
import NotFoundIndicator from 'components/miscellaneous/NotFoundIndicator';
import SectionCard from 'components/layout/SectionCard';

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

type View = 'list' | 'calendar' | 'map';
type Type = 'future' | 'past' | 'favourites';

const MyReservations = ({ userId }: MyReservationsProps) => {
  const classes = useStyles();
  const [type, setType] = useState<Type>('future');
  const [view, setView] = useState<View>('list');
  /* const filters = useMemo(
    () =>
      type === 'favourites'
        ? {}
        : {
            [type === 'past' ? 'registrationStartDateBefore' : 'registrationStartDateAfter']: new Date().toISOString(),
          },
    [type],
  );*/
  const useHook = useMyReservatedSections;
  const { data, error, hasNextPage, fetchNextPage, isFetching } = useHook(userId);
  const sections = useMemo(() => (data !== undefined ? data.pages.map((page) => page.content).flat(1) : []), [data]);
  const isEmpty = useMemo(() => !sections.length && !isFetching, [sections, isFetching]);

  return (
    <>
      <div className={classes.menu}>nico har micropenis</div>
      <Collapse in={view === 'list'}>
        <Pagination fullWidth hasNextPage={hasNextPage} isLoading={isFetching} nextPage={() => fetchNextPage()}>
          {isEmpty && <NotFoundIndicator header={error?.message || 'Fant ingen reservasjoner'} />}
          <div className={classes.list}>
            {sections.map((section) => (
              <SectionCard fullHeight key={section.id} section={section} />
            ))}
          </div>
        </Pagination>
      </Collapse>
    </>
  );
};

export default MyReservations;
