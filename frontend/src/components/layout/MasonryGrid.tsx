// Material UI Components
import { makeStyles, useTheme } from '@material-ui/core/styles';
import { ReactNode } from 'react';

// Project Components
import Masonry from 'react-masonry-css';
import RoomCard from 'components/layout/RoomCard';
const useStyles = makeStyles((theme) => ({
  myMasonryGrid: {
    display: 'flex',
    marginLeft: theme.spacing(-1),
    width: 'auto',
    marginBottom: theme.spacing(1),
    marginTop: theme.spacing(1),
  },
  myMasonryGridColumn: {
    paddingLeft: theme.spacing(1),
    backgroundClip: 'padding-box',
  },
}));

export type RoomCard = {
  children: ReactNode;
};

export default function MasonryGrid(props: RoomCard) {
  const classes = useStyles();
  const theme = useTheme();
  const breakpointColumnsObj = {
    default: 4,
    [theme.breakpoints.values.xl]: 3,
    [theme.breakpoints.values.lg]: 2,
    [theme.breakpoints.values.md]: 1,
  };
  return (
    <Masonry breakpointCols={breakpointColumnsObj} className={classes.myMasonryGrid} columnClassName={classes.myMasonryGridColumn}>
      {props.children}
    </Masonry>
  );
}
