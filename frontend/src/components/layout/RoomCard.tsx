// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardActionArea, CardContent, CardMedia, Typography } from '@material-ui/core/';

// Project Components
import { Link } from 'react-router-dom';
import URLS from 'URLS';
import { RoomList } from 'types/Types';

// Images
import LOGO from 'assets/img/DefaultBackground.jpg';

const useStyles = makeStyles((theme) => ({
  root: {
    marginBottom: theme.spacing(1),
  },
  media: {
    height: 140,
  },
  link: {
    textDecoration: 'none',
  },
  description: {
    overflow: 'hidden',
    '-webkit-line-clamp': 4,
    display: '-webkit-box',
    '-webkit-box-orient': 'vertical',
    whiteSpace: 'break-spaces',
  },
}));

export type RoomCardProps = {
  room: RoomList;
};

const RoomCard = ({ room }: RoomCardProps) => {
  const classes = useStyles();

  return (
    <Link className={classes.link} to={`${URLS.ROOMS}${room.id}`}>
      <Card className={classes.root}>
        <CardActionArea>
          <CardMedia className={classes.media} image={LOGO} />
          <CardContent>
            <Typography component='h2' gutterBottom variant='h5'>
              {room.name}
            </Typography>
            <Typography className={classes.description} variant='body2'>
              {room.building}
            </Typography>
            <Typography className={classes.description} variant='body2'>
              {room.level}
            </Typography>
            <Typography className={classes.description} variant='body2'>
              {room.capacity}
            </Typography>
          </CardContent>
        </CardActionArea>
      </Card>
    </Link>
  );
};

export default RoomCard;
