import classnames from 'classnames';
import { Room } from 'types/Types';
import { useMemo } from 'react';
// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Skeleton from '@material-ui/core/Skeleton';

// Project Components
import Paper from 'components/layout/Paper';
import MasonryGrid from 'components/layout/MasonryGrid';
import SectionCard from 'components/layout/SectionCard';
import Pagination from 'components/layout/Pagination';
import NotFoundIndicator from 'components/miscellaneous/NotFoundIndicator';
import { useSections } from 'hooks/Sections';

const useStyles = makeStyles((theme) => ({
  image: {
    borderRadius: theme.shape.borderRadius,
    border: `1px solid ${theme.palette.divider}`,
  },
  rootGrid: {
    display: 'flex',
    position: 'relative',
    alignItems: 'self-start',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridGap: theme.spacing(2),
    alignItems: 'self-start',
    [theme.breakpoints.down('md')]: {
      gridGap: theme.spacing(1),
    },
  },
  details: {
    padding: theme.spacing(1, 2),
    width: '100%',
  },
  detailsHeader: {
    fontSize: '1.5rem',
  },
  titleRow: {
    gridTemplateColumns: '1fr auto',
  },
  title: {
    color: theme.palette.text.primary,
    fontSize: '2.4rem',
    wordWrap: 'break-word',
  },
  description: {
    whiteSpace: 'break-spaces',
  },
  skeleton: {
    maxWidth: '100%',
    borderRadius: theme.shape.borderRadius,
  },
  img: {
    width: '100%',
    borderRadius: theme.shape.borderRadius,
  },
}));

export type RoomRendererProps = {
  room: Room;
  preview?: boolean;
};

const RoomRenderer = ({ room }: RoomRendererProps) => {
  const classes = useStyles();
  const { data, error, hasNextPage, fetchNextPage, isFetching } = useSections(room.id);
  const sections = useMemo(() => (data !== undefined ? data.pages.map((page) => page.content).flat(1) : []), [data]);
  const isEmpty = useMemo(() => !sections.length && !isFetching, [sections, isFetching]);

  return (
    <div className={classes.rootGrid}>
      <div className={classes.grid}>
        <div>
          <div className={classnames(classes.grid, classes.titleRow)}>
            <Typography className={classes.title} gutterBottom variant='h1'>
              Room: {room.name}
            </Typography>
          </div>
        </div>
        <div className={classes.grid}>
          <Paper>
            <div>
              <Typography className={classes.detailsHeader} variant='h2'>
                Location
              </Typography>
              <Typography>{`Building: ${room.building.name}`}</Typography>
              <Typography>{`Address: ${room.building.address}`}</Typography>
              <Typography>{`Floor level: ${room.level} `}</Typography>
            </div>
          </Paper>
          {/* {isLoading && <ListItemLoading />} */}
          <Pagination fullWidth hasNextPage={hasNextPage} isLoading={isFetching} nextPage={() => fetchNextPage()}>
            {isEmpty && <NotFoundIndicator header={error?.message || 'Couldnt find any sections'} />}
            {sections.map((section) => (
              <SectionCard key={section.id} section={section} />
            ))}
          </Pagination>
          {/* {isFetching && <ListItemLoading />} */}
        </div>
      </div>
    </div>
  );
};

export default RoomRenderer;

export const RoomRendererLoading = () => {
  const classes = useStyles();

  return (
    <div className={classes.rootGrid}>
      <div className={classes.grid}>
        <div>
          <Skeleton className={classes.skeleton} height={80} width='60%' />
          <Skeleton className={classes.skeleton} height={40} width={250} />
          <Skeleton className={classes.skeleton} height={40} width='80%' />
          <Skeleton className={classes.skeleton} height={40} width='85%' />
          <Skeleton className={classes.skeleton} height={40} width='75%' />
          <Skeleton className={classes.skeleton} height={40} width='90%' />
        </div>
        <div className={classes.grid}>
          <Paper>
            <Skeleton className={classes.skeleton} height={80} width='60%' />
            <Skeleton className={classes.skeleton} height={40} width={250} />
            <Skeleton className={classes.skeleton} height={40} width='80%' />
            <Skeleton className={classes.skeleton} height={40} width='85%' />
          </Paper>
        </div>
      </div>
      <div className={classes.grid}>
        <MasonryGrid>
          <Skeleton className={classes.img} height={150} />
          <Skeleton className={classes.img} height={200} />
          <Skeleton className={classes.img} height={180} />
          <Skeleton className={classes.img} height={120} />
        </MasonryGrid>
      </div>
    </div>
  );
};
