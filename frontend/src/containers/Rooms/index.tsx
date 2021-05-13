import { Fragment, useMemo } from 'react';
import Helmet from 'react-helmet';
import { useRooms } from 'hooks/Rooms';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

// Project Components
import Navigation from 'components/navigation/Navigation';
import Pagination from 'components/layout/Pagination';
import Paper from 'components/layout/Paper';
import NotFoundIndicator from 'components/miscellaneous/NotFoundIndicator';

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
}));

const Rooms = () => {
  const classes = useStyles();
  const { data, error, hasNextPage, fetchNextPage, isFetching } = useRooms();
  const isEmpty = useMemo(() => (data !== undefined ? !data.pages.some((page) => Boolean(page.results.length)) : false), [data]);

  return (
    <Navigation topbarVariant='filled'>
      <Helmet>
        <title>Book Room</title>
      </Helmet>
      <div className={classes.wrapper}>
        <Typography variant='h1'>Book Room</Typography>
        <div className={classes.root}>
          {/* {isLoading && <ListItemLoading />} */}
          {isEmpty && <NotFoundIndicator header='Could not find any rooms' />}
          {error && <Paper>{error.detail}</Paper>}
          {data !== undefined && (
            <Pagination fullWidth hasNextPage={hasNextPage} isLoading={isFetching} nextPage={() => fetchNextPage()}>
              <div className={classes.list}>
                {data.pages.map((page, i) => (
                  <Fragment key={i}>
                    {page.results.map((newsItem, j) => (
                      <Typography key={j} variant='h2'>
                        - {newsItem.title}
                      </Typography>
                    ))}
                  </Fragment>
                ))}
              </div>
            </Pagination>
          )}
          {/* {isFetching && <ListItemLoading />} */}
        </div>
      </div>
    </Navigation>
  );
};

export default Rooms;
